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
**Where:**
- `connect4.factory.PieceFactory`
- `connect4.factory.PlayerFactory`
**Why**
All piece and player creation goes through factory classes instead of being scattered across the codebase. This centralizes object creation so that adding a new piece color or player type only requires changing the factory.
---

### 2. Strategy Pattern
**Where:**
- Win Strategy
- `connect4.strategy.WinStrategy` (interface)
- `connect4.strategy.StandardWinStrategy` (implementation)
- `connect4.strategy.MoveStrategy` (interface)
- Move Strategy
- `connect4.strategy.HumanStrategy`(implementation)
- `connect4.strategy.EasyStrategy`(implementation)
- `connect4.strategy.MediumStrategy`(implementation)
- `connect4.strategy.HardStrategy`(implementation)
**Why**
  We used the Strategy pattern in two places. For move selection, each player holds a MoveStrategy — HumanStrategy reads input, EasyStrategy picks randomly, MediumStrategy blocks and wins when possible, and HardStrategy looks ahead using minimax. For win checking, Connect4Game delegates to a WinStrategy. Swapping computer difficulty or win condition only requires writing one new class and we don't have to change anything else.
---

### 3. Observer Pattern
**Where:**
- `connect4.observer.GameObserver` (interface)
- `connect4.observer.ConsoleGameObserver` (implementation)
**Why**
Connect4Game maintains a list of observers and notifies them when something happens : a move is made, someone wins, the game is a draw. This keeps game logic and display logic completely separate. Connect4Game never prints anything itself, it just announces events and observers decide what to do with them.

### 3. Builder Pattern
**Where:**
- `connect4.builder.Connect4GameBuilder` 
**Why**
Setting up a game requires a board, two players, pieces, strategies, and a win strategy. The builder lets you configure all of this step by step through chained method calls and then call build() to get a fully assembled game. GameSetupDialog uses it to turn the user's UI selections into a ready-to-play Connect4Game.

### 3. Command Pattern
**Where:**
- `connect4.command.GameCommand` (interface)
- `connect4.command.DropPieceCommand` (implementation)
**Why**
Every move is wrapped in a DropPieceCommand that saves the game state before executing. If undo is called, it restores that saved state. SwingConnect4 keeps a stack of executed commands and clicking Undo pops the last one and reverts the board. This gives us undo support without putting any undo logic inside Connect4Game itself.



---

##  Foundational Classes and Logic

The project contains fully implemented classes with meaningful logic:

### Board Logic
- `connect4.board.Board` (interface)
- `connect4.board.GridBoard` (implementation)

---
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
- `connect4.view.ConsoleBoardView`
- `connect4.view.SwingConnect4`
- `connect4.view.GameSetupDialog`

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
```

## How to Run
- Run SwingConnect4.main() to launch the GUI. A setup dialog will appear to choose your game mode and difficulty.
- Run Main.main() for the console version.