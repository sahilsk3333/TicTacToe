package com.sahilpc.tictactoe

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sahilpc.tictactoe.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding

    private lateinit var gameManager: GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameManager = GameManager()
        resetBoxes()
        binding.startNewGame.visibility = View.INVISIBLE
        binding.apply {
            one.setOnClickListener { onBoxClicked(one, Position(0, 0)) }
            two.setOnClickListener { onBoxClicked(two, Position(0, 1)) }
            three.setOnClickListener { onBoxClicked(three, Position(0, 2)) }
            four.setOnClickListener { onBoxClicked(four, Position(1, 0)) }
            five.setOnClickListener { onBoxClicked(five, Position(1, 1)) }
            six.setOnClickListener { onBoxClicked(six, Position(1, 2)) }
            seven.setOnClickListener { onBoxClicked(seven, Position(2, 0)) }
            eight.setOnClickListener { onBoxClicked(eight, Position(2, 1)) }
            nine.setOnClickListener { onBoxClicked(nine, Position(2, 2)) }
        }



        binding.startNewGame.setOnClickListener {
            binding.startNewGame.visibility = View.GONE
            gameManager.reset()
            resetBoxes()
        }
        updatePoints()

    }
    private fun resetBoxes() {
        binding.apply {
            one.setImageBitmap(null)
            two.setImageBitmap(null)
            three.setImageBitmap(null)
            four.setImageBitmap(null)
            five.setImageBitmap(null)
            six.setImageBitmap(null)
            seven.setImageBitmap(null)
            eight.setImageBitmap(null)
            nine.setImageBitmap(null)
            one.background = null
            two.background = null
            three.background = null
            four.background = null
            five.background = null
            six.background = null
            seven.background = null
            eight.background = null
            nine.background = null
            one.isEnabled = true
            two.isEnabled = true
            three.isEnabled = true
            four.isEnabled = true
            five.isEnabled = true
            six.isEnabled = true
            seven.isEnabled = true
            eight.isEnabled = true
            nine.isEnabled = true
        }

    }


    private fun disableBoxes() {
        binding.apply {
            one.isEnabled = false
            two.isEnabled = false
            three.isEnabled = false
            four.isEnabled = false
            five.isEnabled = false
            six.isEnabled = false
            seven.isEnabled = false
            eight.isEnabled = false
            nine.isEnabled = false
        }

    }
    private fun onBoxClicked(box: ImageView, position: Position) {
        if (!hasImage(box)){
            if (gameManager.currentPlayerMark == "X"){
                box.setImageResource(R.drawable.x)
            }else{
                box.setImageResource(R.drawable.o)
            }

            val winningLine = gameManager.makeMove(position)
            if (!gameManager.hasGameEndedV2()){
                binding.startNewGame.visibility = View.VISIBLE
            }
            if (winningLine != null) {
                updatePoints()
                disableBoxes()
                binding.startNewGame.visibility = View.VISIBLE
                showWinner(winningLine)

            }
        }


    }

    private fun hasImage(view: ImageView): Boolean {
        val drawable = view.drawable
        var hasImage = drawable != null
        if (hasImage && drawable is BitmapDrawable) {
            hasImage = drawable.bitmap != null
        }
        return hasImage
    }

    private fun updatePoints() {
        binding.playerOneScore.text = "Player X Points: ${gameManager.player1Points}"
        binding.playerTwoScore.text = "Player O Points: ${gameManager.player2Points}"
    }

    private fun showWinner(winningLine: WinningLine) {
        val (winningBoxes, background) = when (winningLine) {
            WinningLine.ROW_0 -> Pair(listOf(binding.one, binding.two, binding.three), R.color.green)
            WinningLine.ROW_1 -> Pair(listOf(binding.four, binding.five, binding.six), R.color.green)
            WinningLine.ROW_2 -> Pair(listOf(binding.seven, binding.eight, binding.nine), R.color.green)
            WinningLine.COLUMN_0 -> Pair(listOf(binding.one, binding.four, binding.seven), R.color.green)
            WinningLine.COLUMN_1 -> Pair(listOf(binding.two, binding.five, binding.eight), R.color.green)
            WinningLine.COLUMN_2 -> Pair(listOf(binding.three, binding.six, binding.nine), R.color.green)
            WinningLine.DIAGONAL_LEFT -> Pair(listOf(binding.one, binding.five, binding.nine),
                R.color.green
            )
            WinningLine.DIAGONAL_RIGHT -> Pair(listOf(binding.three, binding.five, binding.seven),
                R.color.green
            )
        }

        winningBoxes.forEach { box ->
            box.background =
                context?.let { ContextCompat.getDrawable( it.applicationContext, background) }
        }
    }

}


