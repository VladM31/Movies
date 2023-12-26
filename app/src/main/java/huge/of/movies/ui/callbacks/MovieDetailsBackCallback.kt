package huge.of.movies.ui.callbacks

import androidx.activity.OnBackPressedCallback

class MovieDetailsBackCallback(
    private val onBack: () -> Unit
) : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
        onBack()
    }
}