// @author Bhavya Mehta
package com.yongk.listviewfilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yongk.listviewfilter.indexbar.listfiler.DataDealResults;
import com.yongk.listviewfilter.indexbar.listfiler.FilterBackResults;
import com.yongk.listviewfilter.indexbar.view.PinnedHeaderListView;

// Activity that display customized list view and search box
public class MainActivity extends Activity implements DataDealResults<String> {

	// an array of countries to display in the list
	static final String[] ITEMS = new String[] { "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
			"Eritrea", "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland", "Afghanistan",
			"Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica",
			"Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
			"Bangladesh", "Barbados", "Belarus", "Belgium", "Monaco", "Mongolia", "Montserrat", "Morocco",
			"Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
			"New Caledonia", "New Zealand", "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras",
			"Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy",
			"Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia",
			"Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Nicaragua", "Niger",
			"Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Marianas", "Norway", "Oman", "Pakistan",
			"Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",
			"Portugal", "Puerto Rico", "Qatar", "French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana",
			"Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea",
			"Guinea-Bissau", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova",
			"Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
			"Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal", "Seychelles",
			"Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
			"South Georgia and the South Sandwich Islands", "South Korea", "Spain", "Sri Lanka", "Sudan", "Suriname",
			"Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan",
			"Tanzania", "Thailand", "The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago",
			"Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
			"United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands",
			"Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Virgin Islands",
			"Wallis and Futuna", "Western Sahara", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso",
			"Burundi", "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
			"Central African Republic", "Chad", "Chile", "China", "Reunion", "Romania", "Russia", "Rwanda",
			"Sqo Tome and Principe", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia",
			"Saint Pierre and Miquelon", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Christmas Island",
			"Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba",
			"Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica",
			"Dominican Republic", "Former Yugoslav Republic of Macedonia", "France", "French Guiana",
			"French Polynesia", "Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
			"Marshall Islands", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe" };

	// unsorted list items
	ArrayList<String> mItems;

	// custom list view with pinned header
	PinnedHeaderListView mListView;

	// search box
	EditText mSearchView;

	// loading view
	ProgressBar mLoadingView;

	// empty view
	TextView mEmptyView;

	private BrandBarHelper mBrandBarHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// UI elements
		setupViews();

		// Array to ArrayList
		mItems = new ArrayList<String>(Arrays.asList(ITEMS));
		
		mBrandBarHelper = new BrandBarHelper();
		mBrandBarHelper.initListAdapter(mListView, new TestPinnedHeaderAdapter(this), getFilterBackResults());
		mBrandBarHelper.setDataDealResults(this);

		// for handling configuration change
		if (savedInstanceState != null) {
			mBrandBarHelper.setList(
					savedInstanceState.getIntegerArrayList("mListSectionPos"), 
					savedInstanceState.getStringArrayList("mListItems") );

			String constraint = savedInstanceState.getString("constraint");
			if (constraint != null && constraint.length() > 0) {
				mSearchView.setText(constraint);
				setIndexBarViewVisibility(constraint);
			}
		} else {
			poplulateDeal(mItems);
		}
	}

	private void setupViews() {
		setContentView(R.layout.main_act);
		mSearchView = (EditText) findViewById(R.id.search_view);
		mLoadingView = (ProgressBar) findViewById(R.id.loading_view);
		mListView = (PinnedHeaderListView) findViewById(R.id.list_view);
		mEmptyView = (TextView) findViewById(R.id.empty_view);
		
	}

	protected void onPostCreate(Bundle savedInstanceState) {
		mSearchView.addTextChangedListener(filterTextWatcher);
		super.onPostCreate(savedInstanceState);
	}
	
	
	private TextWatcher filterTextWatcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
			String str = s.toString();
			mBrandBarHelper.filter(str);
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}
	};

	private void setIndexBarViewVisibility(String constraint) {
		// hide index bar for search results
		if (constraint != null && constraint.length() > 0) {
			mListView.setIndexBarVisibility(false);
		} else {
			mListView.setIndexBarVisibility(true);
		}
	}
	
	private void showLoading(View contentView, View loadingView, View emptyView) {
		contentView.setVisibility(View.GONE);
		loadingView.setVisibility(View.VISIBLE);
		emptyView.setVisibility(View.GONE);
	}
	
	private void showContent(View contentView, View loadingView, View emptyView) {
		contentView.setVisibility(View.VISIBLE);
		loadingView.setVisibility(View.GONE);
		emptyView.setVisibility(View.GONE);
	}
	
	private void showEmptyText(View contentView, View loadingView, View emptyView) {
		contentView.setVisibility(View.GONE);
		loadingView.setVisibility(View.GONE);
		emptyView.setVisibility(View.VISIBLE);
	}

	private FilterBackResults<String> getFilterBackResults(){
		
		return new FilterBackResults<String>() {
			public ArrayList<String> itemsFilter() {
				return mItems;
			}
			public void publishResults(String constraint, ArrayList<String> filtered) {
				setIndexBarViewVisibility(constraint.toString());
				// sort array and extract sections in background Thread
				poplulateDeal(filtered);
			}
			public boolean seachFilter(String t, String constraintStr) {
				return t.toLowerCase(Locale.getDefault()).startsWith(
						constraintStr);
			}
		};
	}
	
	private void poplulateDeal(ArrayList<String> filtered){
		showLoading(mListView, mLoadingView, mEmptyView);
		mBrandBarHelper.poplulateDeal(filtered);
	}
	
	private void poplulateExecute(ArrayList<Integer> listSectionPos, ArrayList<String> listItems){
		if(listItems!=null){
			showContent(mListView, mLoadingView, mEmptyView);
		}else{
			showEmptyText(mListView, mLoadingView, mEmptyView);
		}
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (mBrandBarHelper.getListItems() != null && mBrandBarHelper.getListItems().size() > 0) {
			outState.putStringArrayList("mListItems", mBrandBarHelper.getListItems());
		}
		if (mBrandBarHelper.getListSectionPos() != null && mBrandBarHelper.getListSectionPos().size() > 0) {
			outState.putIntegerArrayList("mListSectionPos", mBrandBarHelper.getListSectionPos());
		}
		String searchText = mSearchView.getText().toString();
		if (searchText != null && searchText.length() > 0) {
			outState.putString("constraint", searchText);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPostExecute(ArrayList<Integer> listSectionPos,
			ArrayList<String> listItems) {
		poplulateExecute(listSectionPos, listItems);
	}
}
