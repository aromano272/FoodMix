package com.andreromano.foodmix.ui.recipe_details


import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.andreromano.foodmix.R
import com.andreromano.foodmix.domain.model.Direction
import com.andreromano.foodmix.domain.model.Review
import com.andreromano.foodmix.ui.epoxy_models.KotlinEpoxyHolder
import org.joda.time.DateTime
import org.joda.time.Period


@EpoxyModelClass
abstract class RecipeReviewModel : EpoxyModelWithHolder<RecipeReviewModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_recipe_review

    @EpoxyAttribute
    lateinit var review: Review

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onUserClicked: (Review) -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onFavoriteClicked: (Review) -> Unit

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onReplyClicked: (Review) -> Unit


    override fun bind(holder: Holder) = with(holder) {
        iv_avatar.load(review.user.avatarUrl)

        val daysAgo = Period(review.timestamp, DateTime().millis).days
        tv_timestamp.text = "$daysAgo days"

        tv_username.text = review.user.username
        tv_review.text = review.comment
        cb_favorite.isChecked = review.isFavorite


        tv_reply.setOnClickListener {
            onReplyClicked(review)
        }
        iv_avatar.setOnClickListener {
            onUserClicked(review)
        }
        tv_username.setOnClickListener {
            onUserClicked(review)
        }
        fl_favorite.setOnClickListener {
            onFavoriteClicked(review)
        }

    }

    class Holder : KotlinEpoxyHolder() {
        val iv_avatar by bind<ImageView>(R.id.iv_avatar)
        val tv_username by bind<TextView>(R.id.tv_username)
        val tv_timestamp by bind<TextView>(R.id.tv_timestamp)
        val tv_review by bind<TextView>(R.id.tv_review)
        val fl_favorite by bind<View>(R.id.fl_favorite)
        val cb_favorite by bind<CheckBox>(R.id.cb_favorite)
        val tv_reply by bind<TextView>(R.id.tv_reply)
    }
}