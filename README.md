# Exercise project for SadaPay

Hi, I am Mughees Akram. Here is my best try to showcase my skills and understanding of best
practices.

# Tech Stack

In this project, I have used following:

- MVVM
- Retrofit
- Room
- Coroutines
- Hilt
- Kotlin DSL for gradle.
- Lottie

## Architecture

I have structured the architecture keeping in mind the SOLID principles and clean architecture.
Initially, I started with the architecture base that I have developed previously, and then, while,
keeping in mind these principles, I improvised and improved the overall structure and flow.

## MVVM

For MVVM, I initially went with Data binding and thats why I was using 4 file structure for each
screen which are:

- Activity
- ViewModel
- Interface
- State But, then, I realized I dont need Data binding for this project and then I decided to use
  view binding. I have removed State files and its implementation from the base layer.

## TDD

I have tried to achieve maximum code coverage as possbile. Right now, I did not integrated JACOCO to
combine the test coverage of both test and androidTest. I have not covered UI automation test as
much as I should have. But I have tried to cover unit test of data layer and VM as much as possible.

## UI

I have kept UI of this application really simple. I have not implemented material theme.

## Gradle

For gradle, I have used Kotlin DSL. For me, It keeps gradle dependencies neat and more
understandable. 

