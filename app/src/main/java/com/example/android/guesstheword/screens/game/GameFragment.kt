/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding
import timber.log.Timber

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    //The GameViewModel
    private lateinit var gameViewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        // Connect with Game View Model
        Timber.i("Calling ViewModelProvider ")
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)





//        gameViewModel.score.observe(viewLifecycleOwner, Observer {newScore ->
//            binding.scoreText.text = newScore.toString()
//        })
//        gameViewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
//            binding.wordText.text = newWord
//        })
        gameViewModel.eventGameFinished.observe(viewLifecycleOwner, Observer {gameFinished ->
            if(gameFinished){
                val action = GameFragmentDirections.actionGameToScore(gameViewModel.score.value ?: 0)
                findNavController(this).navigate(action)
                gameViewModel.onGameFinishedComplete()
            }
        })
        gameViewModel.currentTime.observe(viewLifecycleOwner, Observer { currentTime ->
            binding.timerText.text = currentTime
        })

        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = this

        return binding.root

    }



    /**
     * Called when the game is finished
     */
    private fun gameFinished() {

    }






    /** Methods for updating the UI **/

}