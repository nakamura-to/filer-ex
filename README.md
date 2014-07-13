Eclipse 4.4 has a limitation of file generation with Pluggable Annotation Processing API.
========================================================================

Overview
-----------

When more than 2000 files are generated
with Pluggable Annotation Processing API in Eclipse,
javax.annotation.processing.FilerException is thrown.

Pluggable Annotation Processing API in javac is no problem.

Maybe this bug is same as [Bug 367599](https://bugs.eclipse.org/bugs/show_bug.cgi?id=367599).

Environment
-----------

### Eclipse

Eclipse Standard/SDK  
Version: Luna Release (4.4.0)  
Build id: 20140612-0600  

### Java

java version "1.8.0_05"  
Java(TM) SE Runtime Environment (build 1.8.0_05-b13)  
Java HotSpot(TM) 64-Bit Server VM (build 25.5-b02, mixed mode)  

Procedure for reproducing
-------------------------

### 1. Import projects into Eclipse workspace

```sh
$ git clone https://github.com/nakamura-to/filer-ex.git
$ cd filer-ex
$ ./gradlew clean cleanEclipse eclipse build
```

Import following projects into Eclipse workspace:

- filer-ex/apt-client
- filer-ex/apt-lib

### 2. Delete all java files in the apt-client project

```sh
$ ./gradlew :apt-client:del
```

Refresh Eclipse workspace.

### 3. Generate annotated java files.

```sh
$ ./gradlew :apt-client:gen
```

Refresh Eclipse workspace.

### 4. See the Eclipse Error Log view.

Following error is shown in the view.

```
null
org.eclipse.jdt.apt.pluggable.core
Error
Sun Jul 13 19:03:00 JST 2014
Exception thrown by Java annotation processor example.MyProcessor@2439e6dc

java.lang.RuntimeException: javax.annotation.processing.FilerException: Source file already created: /apt-client/.apt_generated/example/apt/Class1861.java
	at example.MyProcessor.generate(MyProcessor.java:61)
	at example.MyProcessor.handleAnnotation(MyProcessor.java:45)
	at example.MyProcessor.process(MyProcessor.java:37)
	at org.eclipse.jdt.internal.compiler.apt.dispatch.RoundDispatcher.handleProcessor(RoundDispatcher.java:139)
	at org.eclipse.jdt.internal.compiler.apt.dispatch.RoundDispatcher.round(RoundDispatcher.java:110)
	at org.eclipse.jdt.internal.compiler.apt.dispatch.BaseAnnotationProcessorManager.processAnnotations(BaseAnnotationProcessorManager.java:159)
	at org.eclipse.jdt.internal.apt.pluggable.core.dispatch.IdeAnnotationProcessorManager.processAnnotations(IdeAnnotationProcessorManager.java:134)
	at org.eclipse.jdt.internal.compiler.Compiler.processAnnotations(Compiler.java:818)
	at org.eclipse.jdt.internal.compiler.Compiler.compile(Compiler.java:434)
	at org.eclipse.jdt.internal.core.builder.AbstractImageBuilder.compile(AbstractImageBuilder.java:367)
	at org.eclipse.jdt.internal.core.builder.IncrementalImageBuilder.compile(IncrementalImageBuilder.java:330)
	at org.eclipse.jdt.internal.core.builder.AbstractImageBuilder.compile(AbstractImageBuilder.java:331)
	at org.eclipse.jdt.internal.core.builder.IncrementalImageBuilder.build(IncrementalImageBuilder.java:135)
	at org.eclipse.jdt.internal.core.builder.JavaBuilder.buildDeltas(JavaBuilder.java:267)
	at org.eclipse.jdt.internal.core.builder.JavaBuilder.build(JavaBuilder.java:195)
	at org.eclipse.core.internal.events.BuildManager$2.run(BuildManager.java:734)
	at org.eclipse.core.runtime.SafeRunner.run(SafeRunner.java:42)
	at org.eclipse.core.internal.events.BuildManager.basicBuild(BuildManager.java:206)
	at org.eclipse.core.internal.events.BuildManager.basicBuild(BuildManager.java:246)
	at org.eclipse.core.internal.events.BuildManager$1.run(BuildManager.java:299)
	at org.eclipse.core.runtime.SafeRunner.run(SafeRunner.java:42)
	at org.eclipse.core.internal.events.BuildManager.basicBuild(BuildManager.java:302)
	at org.eclipse.core.internal.events.BuildManager.basicBuildLoop(BuildManager.java:358)
	at org.eclipse.core.internal.events.BuildManager.build(BuildManager.java:381)
	at org.eclipse.core.internal.events.AutoBuildJob.doBuild(AutoBuildJob.java:143)
	at org.eclipse.core.internal.events.AutoBuildJob.run(AutoBuildJob.java:241)
	at org.eclipse.core.internal.jobs.Worker.run(Worker.java:54)
Caused by: javax.annotation.processing.FilerException: Source file already created: /apt-client/.apt_generated/example/apt/Class1861.java
	at org.eclipse.jdt.internal.apt.pluggable.core.filer.IdeFilerImpl.createSourceFile(IdeFilerImpl.java:129)
	at example.MyProcessor.generate(MyProcessor.java:53)
	... 26 more
```
