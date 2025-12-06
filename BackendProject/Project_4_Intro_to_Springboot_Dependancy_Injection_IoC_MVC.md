# Lecture | Intro to Spring Boot, Dependency Injection, IoC, MVC

## Contents

## I] Doubt 1:
Does Monolithic architecture has slow compilation and high application startup time 
because it has huge codebase. Is it true?

### 1.1) Answer

#### (1) Compilation Time Issues

In monolithic applications, the entire codebase is typically housed in a single 
assembly or a few tightly coupled assemblies. When you make even a small change to 
the code, the compiler often needs to recompile the entire application or large 
portions of it. Developers working on large monolithic projects have reported 
compilation times exceeding several minutes for relatively small changes. This happens
because:

- All code dependencies are tightly coupled within the monolith
- The compiler cannot easily isolate and rebuild only the changed components
- Even incremental build features may not fully address the problem in truly large 
  monolithic applications

The solution often involves breaking the monolith into smaller, independent assemblies
so that only the changed modules need recompilation, which can dramatically reduce
build times.

#### (2) Application Startup Time

Large monolithic applications also tend to have longer startup times because:

- The entire application must be loaded into memory at once
- All modules, dependencies, and libraries need to be initialized before the application is ready
- There's no option to load only the needed components

#### (3) Trade-offs

While slow compilation and startup times are legitimate disadvantages as the codebase 
grows, monolithic architecture does offer **faster runtime performance** once started,
with lower latency since all calls are local and there's no inter-service communication
overhead. This is why deployment bottlenecks caused by large codebases are listed as 
a specific challenge requiring CI/CD pipelines and modularization strategies.
