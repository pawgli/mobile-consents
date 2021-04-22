package com.example.mobileconsents.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookieinformation.mobileconsents.ConsentItem
import com.cookieinformation.mobileconsents.TextTranslation
import com.example.mobileconsents.databinding.ListItemConsentBinding
import java.util.*

class ConsentItemAdapter(
    private val consentItems: List<ConsentItem>,
    private val locale: Locale = Locale.GERMAN
) : RecyclerView.Adapter<ConsentItemAdapter.ConsentItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ConsentItemVH(
            ListItemConsentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ConsentItemVH, position: Int) {
        holder.binding.apply {
            itemId.text = consentItems[position].consentItemId.toString()
            itemShortText.text = TextTranslation.getTranslationFor(consentItems[position].shortText, listOf(locale)).text
            itemLongText.text = TextTranslation.getTranslationFor(consentItems[position].longText, listOf(locale)).text
            itemRequired.text = consentItems[position].required.toString()
            itemType.text = consentItems[position].type.toString()
        }
    }

    override fun getItemCount() = consentItems.size

    inner class ConsentItemVH(
        val binding: ListItemConsentBinding
    ) : RecyclerView.ViewHolder(binding.root)
}