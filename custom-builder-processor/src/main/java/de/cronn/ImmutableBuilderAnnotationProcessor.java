package de.cronn;

import static javax.tools.Diagnostic.Kind.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

@SupportedAnnotationTypes({
	"de.cronn.ImmutableBuilder",
	"de.cronn.ImmutableBuilderSetter"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ImmutableBuilderAnnotationProcessor extends AbstractProcessor {

	private static final String TEMPLATES = """
		main(package, builder, immutable, original, setters) ::= <<
		package <package>;

		public class <builder> {

			public static class <immutable> extends <original> {
				private <immutable>(
							<setters:as_argument(); separator=",\\n">
					) {

					<setters:as_supercall(); separator="\\n">
				}

				<setters:throwing_setter(); separator="\\n\\n">
			}

			<setters:as_field(); separator="\\n">

			public <builder>() {
			}

			<setters:setter_implementation(builder); separator="\\n\\n">

			public <immutable> build() {
				return new <immutable>(
					<setters:valuename(); separator=",\\n">
				);
			}
		}

		>>

		throwing_setter(setter) ::= <<
		@Override
		public void <setter.name>(<setter.type> value) {
			throw new RuntimeException("This object " + this + " is immutable!");
		}
		>>

		setter_implementation(setter, builder) ::= <<
		public <builder> <setter.name>(<setter.type> value) {
			<setter:valuename()> = value;
			return this;
		}
		>>

		as_argument(setter) ::= "<setter.type> <setter:valuename()>"
		as_supercall(setter) ::= "super.<setter.name>(<setter:valuename()>);"
		as_field(setter) ::= "private <setter.type> <setter:valuename()>;"
		valuename(setter) ::= "valueFrom_<setter.name>"
		""";

	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);

		processingEnv.getMessager().printMessage(NOTE, "Generate CustomBuilders");
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		LinkedHashMap<Element, Map<String, String>> classSetters = new LinkedHashMap<>();
		for (Element setter : roundEnv.getElementsAnnotatedWith(ImmutableBuilderSetter.class)) {
			Map<String, String> setters = classSetters.computeIfAbsent(setter.getEnclosingElement(), el -> new LinkedHashMap<>());
			String setterName = setter.getSimpleName().toString();
			List<? extends TypeMirror> parameterTypes = ((ExecutableType) setter.asType()).getParameterTypes();
			if (parameterTypes.size() == 1) {
				setters.put(setterName, parameterTypes.get(0).toString());
			} else {
				processingEnv.getMessager().printMessage(WARNING, "Wrong number of setter arguments for " + setterName);
			}
		}
		System.out.println(classSetters);

		for (Element classElement : roundEnv.getElementsAnnotatedWith(ImmutableBuilder.class)) {
			String packageName = classElement.getEnclosingElement().toString();
			String className = classElement.getSimpleName().toString();
			process(packageName, className, classSetters.getOrDefault(classElement, new LinkedHashMap<>()));
		}

		return false;
	}

	private void process(String packageName, String className, Map<String, String> setters) {
		processingEnv.getMessager().printMessage(NOTE, "Generate Immutable Builder for " + packageName + "." + className);

		try {
			JavaFileObject sourceFile = processingEnv
				.getFiler()
				.createSourceFile(packageName + ".Immutable" + className + "Builder");

			try (Writer classWriter = new BufferedWriter(sourceFile.openWriter())) {
				classWriter.write(createContent(packageName, className, setters));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String createContent(String packageName, String className, Map<String, String> setters) throws IOException {
		STGroup groups = new STGroupString(TEMPLATES);
		ST st = groups.getInstanceOf("main");
		st.add("package", packageName);
		st.add("builder", "Immutable" + className + "Builder");
		st.add("immutable", "Immutable" + className);
		st.add("original", className);
		for (String s : setters.keySet()) {
			st.addAggr("setters.{name, type}", s, setters.get(s));
		}
		return st.render();
	}

}
