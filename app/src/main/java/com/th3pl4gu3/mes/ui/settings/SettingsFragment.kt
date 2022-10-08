package com.th3pl4gu3.mes.ui.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.utils.extensions.navigateTo

class SettingsFragment : PreferenceFragmentCompat() {

    private var mViewModel: SettingsViewModel? = null

    private val viewModel get() = mViewModel!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initiate View Model
        mViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        /*
        * The actual xml file that represents the
        * settings screen
        */
        setPreferencesFromResource(R.xml.xml_settings_main, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appThemePreference()

        bugReportPreference()

        serviceSuggestionPreference()
    }

    // Preferences
    private fun appThemePreference() {
        findPreference<ListPreference>(getString(R.string.settings_key_display_theme))?.setOnPreferenceChangeListener { preference, newValue ->
            viewModel.updateAppTheme(preference.key, newValue as String)
            true
        }
    }

    private fun bugReportPreference() {
        findPreference<Preference>(getString(R.string.settings_key_help_feedback))?.setOnPreferenceClickListener {
            navigateTo(SettingsFragmentDirections.actionFragmentSettingsToFragmentBugReport())
            true
        }
    }

    private fun serviceSuggestionPreference() {
        findPreference<Preference>(getString(R.string.settings_key_help_add_services))?.setOnPreferenceClickListener {
            navigateTo(SettingsFragmentDirections.actionFragmentSettingsToFragmentServiceSuggestion())
            true
        }
    }
}