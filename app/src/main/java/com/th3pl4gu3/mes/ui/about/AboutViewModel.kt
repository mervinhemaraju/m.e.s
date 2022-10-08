package com.th3pl4gu3.mes.ui.about

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.th3pl4gu3.mes.BuildConfig
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.ui.utils.extensions.requireStringRes

class AboutViewModel(application: Application) : AndroidViewModel(application) {

    /* The development list */
    internal fun getDevelopmentList() = ArrayList<AboutItem>().apply {
        add(
            AboutItem(
                requireStringRes(R.string.text_title_about_dev_rate),
                requireStringRes(R.string.text_description_about_dev_rate),
                R.drawable.ic_rate,
                AboutItem.Item.DEV_RATE_US
            )
        )
        add(
            AboutItem(
                requireStringRes(R.string.text_title_about_dev_share),
                requireStringRes(R.string.text_description_about_dev_share),
                R.drawable.ic_share,
                AboutItem.Item.DEV_SHARE
            )
        )
        add(
            AboutItem(
                requireStringRes(R.string.text_title_about_dev_suggestion),
                requireStringRes(R.string.text_description_about_dev_suggestion),
                R.drawable.ic_suggestion,
                AboutItem.Item.DEV_SUGGESTION
            )
        )
        add(
            AboutItem(
                requireStringRes(R.string.text_title_about_dev_bug),
                requireStringRes(R.string.text_description_about_dev_bug),
                R.drawable.ic_bug_report,
                AboutItem.Item.DEV_REPORT_BUG
            )
        )
    }

    /* The others list */
    internal fun getOtherList() = ArrayList<AboutItem>().apply {
        add(
            AboutItem(
                requireStringRes(R.string.text_title_about_other_license),
                requireStringRes(R.string.text_title_about_other_license),
                R.drawable.ic_copyright,
                AboutItem.Item.OTHER_LICENSES
            )
        )
        add(
            AboutItem(
                requireStringRes(R.string.text_title_about_other_developer),
                requireStringRes(R.string.text_description_about_other_developer),
                R.drawable.ic_developer,
                AboutItem.Item.OTHER_DEVELOPER
            )
        )
        add(
            AboutItem(
                requireStringRes(R.string.text_title_about_other_terms),
                requireStringRes(R.string.text_title_about_other_terms),
                R.drawable.ic_policy,
                AboutItem.Item.OTHER_POLICY
            )
        )
        add(
            AboutItem(
                requireStringRes(R.string.text_title_about_other_version),
                BuildConfig.VERSION_NAME,
                R.drawable.ic_version,
                AboutItem.Item.OTHER_VERSION
            )
        )
    }
}