# Connect 4 Game – Object-Oriented Design Project

## Team Members
- Trisha Nookala
- Soumya Devulapalli

##  Development Environment
- Language: Java
- IDE: IntelliJ IDEA
- Build Tool: Gradle

---

## 🎯 Project Description

This project implements a console-based version of the classic Connect 4 game. The game is played on a 6×7 grid where two players take turns dropping pieces into columns. The goal is to connect four of the same colored pieces in a row (horizontally, vertically, or diagonally).

The system is designed using object-oriented principles and multiple design patterns to ensure modularity, flexibility, and maintainability.

---

## Design Patterns Used

### 1. Factory Pattern

- `connect4.factory.PieceFactory`
- `connect4.factory.PlayerFactory`

---

### 2. Strategy Pattern
**Where:**
- `connect4.strategy.WinStrategy` (interface)
- `connect4.strategy.StandardWinStrategy` (implementation)

---

### 3. Observer Pattern
**Where:**
- `connect4.observer.GameObserver` (interface)
- `connect4.observer.ConsoleGameObserver` (implementation)



---

##  Foundational Classes and Logic

The project contains fully implemented classes with meaningful logic:

### Board Logic
- `connect4.board.Board` (interface)
- `connect4.board.GridBoard` (implementation)
### Game Logic
- `connect4.controller.Connect4Game`


---

### Player & Pieces
- `connect4.player.Player` (interface)
- `connect4.player.HumanPlayer`
- `connect4.piece.GamePiece` (interface)
- `connect4.piece.RedPiece`, `BluePiece`

**Responsibilities:**
- Represent players and their assigned pieces
- Provide piece symbols and color identity

---

### View
- `connect4.view.ConsoleBoardView`

**Responsibilities:**
- Displaying the board in the terminal

---

## Object-Oriented Principles

### 1. Coding to Abstractions
- `Board`
- `Player`
- `GamePiece`
- `WinStrategy`
- `GameObserver`


---

### 2. Polymorphism
Polymorphism is demonstrated through:
- `RedPiece` and `BluePiece` implementing `GamePiece`
- `HumanPlayer` implementing `Player`
- `StandardWinStrategy` implementing `WinStrategy`
- `ConsoleGameObserver` implementing `GameObserver`

This allows the program to treat different implementations uniformly.

---

### 3. Dependency Injection
Dependencies are passed into classes instead of being created inside them.

Example from `Connect4Game`:
```java
new Connect4Game(board, players, strategy);