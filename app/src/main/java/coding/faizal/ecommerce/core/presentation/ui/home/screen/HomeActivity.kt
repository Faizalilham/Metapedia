package coding.faizal.ecommerce.core.presentation.ui.home.screen

import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import java.util.ArrayList
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.presentation.ui.favorite.screen.FavoriteFragment
import coding.faizal.ecommerce.presentation.ui.profile.screen.ProfileFragment
import coding.faizal.ecommerce.presentation.ui.transaction.screen.TransactionFragment
import com.wwdablu.soumya.lottiebottomnav.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), ILottieBottomNavCallback {
    private var transaction: FragmentTransaction? = null
    private lateinit var bottomNav: LottieBottomNav
    private lateinit var list: ArrayList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNav = findViewById(R.id.bottom_navi)

        // Set font item
        val fontItem = FontBuilder.create("Dashboard")
            .selectedTextColor(Color.BLACK)
            .unSelectedTextColor(Color.GRAY)
            .selectedTextSize(16) // SP
            .unSelectedTextSize(12) // SP
            .setTypeface(Typeface.createFromAsset(assets, "poppins.ttf"))
            .build()

        // Menu Dashboard
        val item1 = MenuItemBuilder.create("home.json", MenuItem.Source.Assets, fontItem, "dash")
            .pausedProgress(1f)
            .loop(false)
            .build()

        // Example Spannable String (at Menu Gifts)
        val spannableString = SpannableString("Gifts")
        spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Menu Gifts
        val item2 = MenuItemBuilder.createFrom(item1, fontItem)
            .selectedLottieName("favorite.json")
            .unSelectedLottieName("favorite.json")
            .pausedProgress(0.75f)
            .build()

        // Menu Mail
        val item3 = MenuItemBuilder.createFrom(item1, fontItem)
            .selectedLottieName("transaction.json")
            .unSelectedLottieName("transaction.json")
            .pausedProgress(0.75f)
            .build()

        // Menu Settings
        val item4 = MenuItemBuilder.createFrom(item1, fontItem)
            .selectedLottieName("home.json")
            .unSelectedLottieName("home.json")
            .build()

        list = ArrayList(4)
        list.add(item1)
        list.add(item2)
        list.add(item3)
        list.add(item4)

        bottomNav.setCallback(this)
        bottomNav.setMenuItemList(list)
        bottomNav.selectedIndex = 0 // first selected index

        // First selected fragment
        if(intent.getStringExtra(FAVORITE) == FAVORITE){
            setFragment(FavoriteFragment())
        }else if(intent.getStringExtra(TRANSACTION) == TRANSACTION){
            setFragment(TransactionFragment())
        }else{
            setFragment(HomeFragment())
        }
    }

    override fun onMenuSelected(oldIndex: Int, newIndex: Int, menuItem: MenuItem) {
        when (newIndex) {
            0 -> setFragment(HomeFragment())
            1 -> setFragment(FavoriteFragment())
            2 -> setFragment(TransactionFragment())
            3 -> setFragment(ProfileFragment())
        }
    }

    private fun setFragment(fragment: Fragment) {
        transaction = supportFragmentManager.beginTransaction()
        transaction?.replace(R.id.fragmentContainerView2, fragment)?.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onAnimationStart(index: Int, menuItem: MenuItem) {
    }

    override fun onAnimationEnd(index: Int, menuItem: MenuItem) {
    }

    override fun onAnimationCancel(index: Int, menuItem: MenuItem) {
    }

    companion object{
        const val HOME = "home"
        const val FAVORITE = "favorite"
        const val TRANSACTION = "transaction"
    }
}
