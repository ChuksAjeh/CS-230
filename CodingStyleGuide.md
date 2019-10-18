# CS-230 Java Style Guide

This document will serve as this groups definition for Java code styling.

## Source files

All source files will contain a single single top-level class, no sub classes.

## Source file structure

In order, each source file will contain the following:

* Package statement(s)
* Import statement(s)
* Exactly one top-level class

Exactly one blank line separates each section that is present.

No wildcard imports are permitted.

## Class structure

Class methods will be in the following order:

* Constructors, in a logical order
* Set methods, in the same order as class variable declaration
* Get methods, in the same order as class variable declaration
* Everything else, in a logical order

## Whitespace

The only whitespace character allowed will be `0x20', also known as a horizontal space character.

Tabs will not be used for indentation and should not be used in string literals.

## Declarations

Only one declaration per line, no doubling up.

Horizontal alignment of class variables will not be required.

```java
private int x; // this is ok
private Color color; // this is also ok

private int   x;      // this is not ok
private Color color;  // do not do this
```

Arrays should be declared thusly:

```java
int[] grid {
    0, 1, 2, 3,
    4, 5, 6, 7
}
```

No C style declarations.

## Brackets

Brackets will be done using Kernighan and Richie styling:

* No line break before the opening brace.
* Line break after the opening brace.
* Line break before the closing brace.

```java
while (someCondition) {
    // Magical stuff
}

if (someOtherCondition) {
    // Do magical stuff
} else (anotherCondition) {
    // Do other magical stuff
}
```

Empty blocks may be simplified:

```java
void (doNothing) {}
```

## Indentation

4 spaces for indentation will be used throughout, no exceptions.

## Indent levels

No more than 5 indent levels will be permitted, this is to keep code as clear and as easy to read as possible.

This includes forced indents by class and method declaration.

## Column Limit

100 characters.

## Comments

All classes, variables and methods should be JavaDoc commented.

## Class, Variable and Method naming

Class names will be in UpperCamelCase.

Variable names will be in lowerCamelCase.

Static variables will be in UpperCamelCase, not block capitals.

Method names will be lowerCamelCase and start with a logical identifier.

## Exceptions

Empty catch() blocks are allowed.