package online.osslab.demo.floatingsearch;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import online.osslab.demo.floatingsearch.data.ColorSuggestion;
import online.osslab.demo.floatingsearch.data.DataHelper;

import java.util.List;

import online.osslab.FloatingSearchView;
import online.osslab.suggestions.SearchSuggestionsAdapter;
import online.osslab.suggestions.model.SearchSuggestion;
import online.osslab.util.view.BodyTextView;
import online.osslab.util.view.IconImageView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private FloatingSearchView searchView;

    private ViewGroup parentView;
    private TextView colorName;
    private TextView colorValue;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentView = (ViewGroup)findViewById(R.id.parent_view);

        searchView = (FloatingSearchView)findViewById(R.id.floating_search_view);
        colorName = (TextView)findViewById(R.id.color_name_text);
        colorValue = (TextView)findViewById(R.id.color_value_text);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        //sets the background color
        refreshBackgroundColor("Blue", "#1976D2");

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    searchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    DataHelper.find(MainActivity.this, newQuery, new DataHelper.OnFindResultsListener() {

                        @Override
                        public void onResults(List<ColorSuggestion> results) {

                            //this will swap the data and
                            //render the collapse/expand animations as necessary
                            searchView.swapSuggestions(results);

                            //let the users know that the background
                            //process has completed
                            searchView.hideProgress();
                        }
                    });
                }

                Log.d(TAG, "onSearchTextChanged()");
            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

                ColorSuggestion colorSuggestion = (ColorSuggestion) searchSuggestion;
                refreshBackgroundColor(colorSuggestion.getColor().getName(), colorSuggestion.getColor().getHex());

                Log.d(TAG, "onSuggestionClicked()");

            }

            @Override
            public void onSearchAction() {

                Log.d(TAG, "onSearchAction()");
            }
        });

        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                //show suggestions when search bar gains focus (typically history suggestions)
                searchView.swapSuggestions(DataHelper.getHistory(MainActivity.this, 3));

                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {

                Log.d(TAG, "onFocusCleared()");
            }
        });

        //handle menu clicks the same way as you would
        //in a regular activity
        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.action_change_colors) {

                    //demonstrate setting colors for items
                    searchView.setBackgroundColor(Color.parseColor("#ECE7D5"));
                    searchView.setViewTextColor(Color.parseColor("#657A81"));
                    searchView.setHintTextColor(Color.parseColor("#596D73"));
                    searchView.setActionMenuOverflowColor(Color.parseColor("#B58900"));
                    searchView.setMenuItemIconColor(Color.parseColor("#2AA198"));
                    searchView.setLeftActionIconColor(Color.parseColor("#657A81"));
                    searchView.setClearBtnColor(Color.parseColor("#D30102"));
                    searchView.setSuggestionRightIconColor(Color.parseColor("#BCADAD"));
                    searchView.setDividerColor(Color.parseColor("#dfd7b9"));

                } else {

                    //just print action
                    Toast.makeText(getApplicationContext(), item.getTitle(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHamburger"
        searchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                Log.d(TAG, "onMenuOpened()");

                drawerLayout.openDrawer(GravityCompat.START);
            }

            @Override
            public void onMenuClosed() {
                Log.d(TAG, "onMenuClosed()");

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        searchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {

                Log.d(TAG, "onHomeClicked()");
            }
        });

        /*
         * Here you have access to the left icon and the text of a given suggestion
         * item when as it is bound to the suggestion list. You can utilize this
         * callback to change some properties of the left icon and the text. For example, you
         * can load left icon images using your favorite image loading library, or change text color.
         *
         * Some restrictions:
         * 1. You can modify the height, eidth, margin, or padding of the text and left icon.
         * 2. You can't modify the text's size.
         *
         * Modifications to these properties will be ignored silently.
         */
        searchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(IconImageView leftIcon, BodyTextView bodyText, SearchSuggestion item, int itemPosition) {

                ColorSuggestion colorSuggestion = (ColorSuggestion) item;

                if (colorSuggestion.getIsHistory()) {
                    leftIcon.setImageDrawable(leftIcon.getResources().getDrawable(R.drawable.ic_history_black_24dp));
                    leftIcon.setAlpha(.36f);
                } else
                    leftIcon.setImageDrawable(new ColorDrawable(Color.parseColor(colorSuggestion.getColor().getHex())));
            }

        });

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                //since the drawer might have opened as a results of
                //a click on the left menu, we need to make sure
                //to close it right after the drawer opens, so that
                //it is closed when the drawer is  closed.
                searchView.closeMenu(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    private void refreshBackgroundColor(String colorName, String colorValue){

        int color = Color.parseColor(colorValue);
        Palette.Swatch swatch = new Palette.Swatch(color, 0);

        this.colorName.setTextColor(swatch.getTitleTextColor());
        this.colorName.setText("不要因为也许会改变\n就不肯说那句美丽的誓言；\n\n不要因为也许会分离\n就不敢求一次倾心的相遇。");

        this.colorValue.setTextColor(swatch.getBodyTextColor());
        //this.colorValue.setText(colorValue);

        parentView.setBackgroundColor(color);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getDarkerColor(color, .8f));

    }

    private static int getDarkerColor(int color, float factor) {

        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a, Math.max((int)(r * factor), 0), Math.max((int)(g * factor), 0),
                Math.max((int)(b * factor), 0));
    }

}
