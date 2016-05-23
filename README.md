Soft Physics Engine
===================

![alt text] (https://raw.githubusercontent.com/exigow/soft-physics-engine/master/screenshots/logo.png "Soft Physics Engine logo")

A simple 2D soft physics engine fully written in Java.

Design goal
-----------

The main goal is to provide easy-to-use, feature-rich physics engine completely written in Java, where each model can be parametrized on-the-fly during simulation by simply assigning the new value of the field. It is as simple as it sounds.

Demos
-----

Library contains several usage examples in an interesting way, or showing some feature.

* [Cloth rendering demo] (https://github.com/exigow/soft-physics-engine/tree/master/src/demos/cloth) ([screenshot] (https://raw.githubusercontent.com/exigow/soft-physics-engine/master/screenshots/cloth-screenshot.png)) - textured grid-based cloth
* [Tree demo] (https://github.com/exigow/soft-physics-engine/tree/master/src/demos/tree) ([screenshot] (https://raw.githubusercontent.com/exigow/soft-physics-engine/master/screenshots/tree-demo-screenshot.png)) - building tree with angle joint
* [Rope rendering demo] (https://github.com/exigow/soft-physics-engine/tree/master/src/demos/rope) ([screenshot] (https://raw.githubusercontent.com/exigow/soft-physics-engine/master/screenshots/rope-demo-screenshot.png)) - textured rope smoothed using Bezier curves
* [Angle joint demo] (https://github.com/exigow/soft-physics-engine/tree/master/src/demos/angle) - stiffened rope with angle joint

Demos are interactive. You can grab every particle using *left mouse button* on desktop. Multi-touch has been tested on mobile devices too.

Dependencies
------------

* [libGDX] (https://github.com/libgdx/libgdx) - cross-platform Java game development framework

    Used **only to show demos** including window creation, particles/joints rendering, texturing, mouse gripping, etc. This part can be easily converted into something else like Slick2d or raw LWJGL.

* [JOML] (https://github.com/JOML-CI/JOML) -  Java math library for OpenGL rendering calculations

    Responsible for engine's math. This library is fast, GC-friendly and produces clean code (this is the reason why I do not use the built-in math with libGDX, which is fast ofc, but not readable).

Future
------

Things to do:

* parts of the world are just mutable DTO's (data transfer objects), created using static inner classes (builders or smth) to achieve major design goal (still wip)
* add minimal sample to README.md
* collisions (to debate)
* fixed constant length joint (temporarily it can be achieved with 'hard spring' but may cause some glitches)
* vector calculations on primitive types, losing code readability :( (or find another way to improve the performance of the GC)
* particle weight
