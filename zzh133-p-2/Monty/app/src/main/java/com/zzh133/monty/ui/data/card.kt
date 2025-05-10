package com.zzh133.monty.ui.data

data class card(
    val suit: String,
    val value: String,
    val isWinningCard: Boolean = false,
    val isFaceUp: Boolean = false
)


fun generateFullDeck(): List<card> {
    val suits = listOf("♠", "♥", "♣", "♦")
    val values = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")

    return suits.flatMap { suit ->
        values.map { value ->
            card(suit = suit, value = value)
        }
    }
}

fun generateGameCards(count: Int): List<card> {
    val fullDeck = generateFullDeck().shuffled()
    val selected = fullDeck.take(count).toMutableList()
    val winIndex = (0 until count).random()
    selected[winIndex] = selected[winIndex].copy(isWinningCard = true)
    return selected
}
