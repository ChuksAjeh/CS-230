# CS-230 Java Style Guide

This document will serve as this groups definition for Java code styling.

## Language

British English wil be used throughout, "colour" is spelt with a u.

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

All Classes will be structured in the following order:

* Javadoc for class
* Class declaration
* Class constant variables
* All other class variables
* Instance variables
* Constructors, in a logical order
* Set methods, in the same order as class variable declaration
* Get methods, in the same order as class variable declaration
* Everything else, in a logical order

Modifiers will be in the following order:

* `public`
* `protected`
* `package-private`
* `private`

## Modifiers

Modifiers will be in the following order:

* public / protected / private
* abstract
* static
* final

## Whitespace

The only whitespace character allowed will be `0x20', also known as a horizontal space character.

Tabs will not be used for indentation and should not be used in string literals.

* Binary operators will have spaces between them with the excpetion of `.`.
* Unary operators will never have a leading space.

## Declarations

Only one declaration per line, no doubling up.

Horizontal alignment of variables will not be permitted.

Declarations should be somewhat near where the variable is being used to minimse their scope.

```java
private int x; // this is ok
private Color colour; // this is also ok

private int   x;      // this is not ok
private Color colour;  // do not do this
```

Arrays should be declared thusly:

```java
int[] grid {
    0, 1, 2, 3,
    4, 5, 6, 7
}
```

No C style declarations.

## Yoda Conditions

Conditional Statements will use Yoda Declarations.

```java
if (42 == value) {
    // Some Magical Stuff
}
// Reads like: "If 42 equals the value..."
```

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

## Break;

W`break;` and `continue;` statements will not be permitted.

## Magic Numbers

Magic numbers will not be permitted.

## Indentation

4 spaces for indentation will be used throughout, no exceptions.

## Indent levels

No more than 4 indent levels will be permitted inside class methods.

## Column Limit

80 character limits are fucking stupid.

Just keep stuff short I guess.. 100 is nicer.

## Comments

JavaDoc all of the things.

## Class, Variable and Method naming

Class names will be in UpperCamelCase.

Variable names will be in lowerCamelCase.

Constant variables will be in FULL_BLOCK_CAPTIALS.

Method names will be lowerCamelCase and start with a logical identifier.

## Exceptions

Empty catch() blocks are allowed.