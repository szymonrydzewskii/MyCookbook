package com.example.mycookbook

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRecipeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
        val ingredientsEditText: EditText = view.findViewById(R.id.ingredientsEditText)
        val instructionEditText: EditText = view.findViewById(R.id.instructionEditText)
        val ratingBar: RatingBar = view.findViewById(R.id.recipeRating)
        val addButton: Button = view.findViewById(R.id.add_recipe_button)

        val sharedPreferences = activity?.getSharedPreferences("recipes", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val gson = Gson()

        val recipeListJson = sharedPreferences?.getString("recipe_list", "[]")
        val type = object : TypeToken<MutableList<Recipe>>() {}.type
        val recipeList: MutableList<Recipe> = gson.fromJson(recipeListJson, type) ?: mutableListOf()

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val ingredients = ingredientsEditText.text.toString()
            val instructions = instructionEditText.text.toString()
            val rating = ratingBar.rating

            if (name.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty()) {
                val newRecipe = Recipe(name, ingredients, instructions, rating)
                recipeList.add(newRecipe)

                val updatedListJson = gson.toJson(recipeList)
                editor?.putString("recipe_list", updatedListJson)
                editor?.apply()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddRecipeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddRecipeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}