package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel: ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer:CountDownTimer

    private val _currentTime = MutableLiveData<String>()
    val currentTime : LiveData<String>
    get() = _currentTime
    // The current word
    private val _word = MutableLiveData<String>()
    val word : LiveData<String>
    get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

    // A boolean that checks if game has finished
    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
    get() = _eventGameFinished


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
    Timber.i("GameViewModel Created!")


        _score.value = 0
        _word.value = ""
        _eventGameFinished.value = false
        resetList()
        nextWord()

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(p0: Long) {
                _currentTime.value = DateUtils.formatElapsedTime(p0/ ONE_SECOND)
            }

            override fun onFinish() {
                _eventGameFinished.value = true
            }
        }
        timer.start()
        //DateUtils.formatElapsedTime()

    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Timber.i("GameViewModel Destroyed!")
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
            //_eventGameFinished.value = true
        }
        _word.value = wordList.removeAt(0)

    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    fun onGameFinishedComplete(){
        _eventGameFinished.value = false
    }
}