## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Architecture](#architecture)
- [Demo](#demo)

## Introduction
- Demonstration application for LiveScore

## Features

List of features that build in this app

* Show all participating team
* Show all previous and upcoming matches
* View team detail with previous matches and upcoming matches of that team
* View match detail with highlight
* Add match to watchlist
* Manage watchlist matches (remove/add)
* Create notification for watching match
* Unit test

## Architecture

* Clean architecture with MVVM model
* UI layer -> Data layer[Repository -> [RemoteSource, LocalSource]]

## Tech-Stack
* Kotlin Coroutine, Flow -> async programming
* Jetpack Navigation -> single activity
* Jetpack Room -> database
* Koin -> dependencies injection
* Kotlin-Serialization -> json serialize
* Ktor -> network
* Coil -> image load
* ExoPlayer -> play video
* Mockk, Turbine -> Unit test

## Demo
![Demo gift](asset/demo.gif)
