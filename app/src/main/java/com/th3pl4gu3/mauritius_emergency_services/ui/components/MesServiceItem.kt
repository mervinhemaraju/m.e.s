package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.flowlayout.FlowRow
import com.th3pl4gu3.mauritius_emergency_services.R
import com.th3pl4gu3.mauritius_emergency_services.models.Service
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.capitalize
import com.th3pl4gu3.mauritius_emergency_services.ui.extensions.isTollFree


@Composable
@ExperimentalMaterial3Api
fun MesServiceItemExtras(
    service: Service,
    extrasClickAction: (Service, String) -> Unit,
    modifier: Modifier
) {
    // Create a list of other contacts
    // from emails and other_contacts
    val contacts: MutableList<Pair<ImageVector, String>> =
        mutableListOf<Pair<ImageVector, String>>().apply {
            addAll(
                service.emails.map { Pair(Icons.Outlined.Email, it) }
            )

            addAll(
                service.other_contacts.map { Pair(Icons.Outlined.Phone, it.toString()) }
            )
        }

    if (contacts.size > 0) {
        Text(
            text = stringResource(id = R.string.title_services_page_other_contacts),
            style = MaterialTheme.typography.bodyLarge,
            softWrap = true,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            modifier = modifier
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
        )

        FlowRow(
            modifier = modifier
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
        ) {
            contacts.forEach {

                TextButton(
                    onClick = { extrasClickAction(service, it.second) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = it.first,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = it.second,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

            }
        }
    } else {
        Text(
            stringResource(id = R.string.message_no_other_contacts_found),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = modifier
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MesSwipeAbleServiceItem(
    service: Service,
    actionVisible: Boolean,
    onClick: () -> Unit,
    extrasClickAction: (Service, String) -> Unit,
    enableSwipeAction: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.background,
    actionSettledContainerColor: Color = MaterialTheme.colorScheme.inverseSurface,
    actionActiveContainerColor: Color = MaterialTheme.colorScheme.primary,
    actionActiveContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier
) {
    val threshold = 0.5f
    var state: SwipeToDismissBoxState? = null
    var expanded by remember {
        mutableStateOf(false)
    }

    state = rememberSwipeToDismissBoxState(
        positionalThreshold = {
            it * threshold
        },
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart && state!!.progress > threshold) {
                onClick()
            }
            false
        }
    )

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        state = state,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = enableSwipeAction,
        backgroundContent = {
            val color by animateColorAsState(
                if (state.targetValue == SwipeToDismissBoxValue.Settled) {
                    actionSettledContainerColor
                } else {
                    actionActiveContainerColor
                }, label = ""
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color),
            ) {
                MesIcon(
                    imageVector = Icons.Outlined.Call,
                    tint = actionActiveContentColor,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(16.dp)
                )
            }
        }
    ) {

        Card(
            shape = RectangleShape,
            colors = CardDefaults.outlinedCardColors(
                containerColor = if (expanded) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    containerColor
                }
            )
        ) {
            ConstraintLayout(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = if (actionVisible) {
                            {}
                        } else {
                            onClick
                        }
                    )

            ) {

                val (
                    textTitle,
                    textSubtitle,
                    iconEmergency,
                    iconDropDown,
                    badge
                ) = createRefs()

                MesAsyncRoundedImage(
                    service = service,
                    modifier = Modifier
                        .size(54.dp)
                        .constrainAs(iconEmergency) {
                            with(16.dp) {
                                start.linkTo(parent.start, this)
                                bottom.linkTo(parent.bottom, this)
                                top.linkTo(parent.top, this)
                            }
                        }
                )

                Text(
                    text = service.name.capitalize(),
                    style = MaterialTheme.typography.bodyMedium,
                    softWrap = true,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .constrainAs(textTitle) {
                            top.linkTo(iconEmergency.top)
                            bottom.linkTo(textSubtitle.top, 4.dp)
                            linkTo(
                                start = iconEmergency.end,
                                end = if (actionVisible) iconDropDown.start else parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            width = Dimension.fillToConstraints
                        }
                )

                Text(
                    text = service.main_contact.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .constrainAs(textSubtitle) {
                            top.linkTo(textTitle.bottom)
                            bottom.linkTo(iconEmergency.bottom)
                            linkTo(
                                start = iconEmergency.end,
                                end = if (actionVisible) iconDropDown.start else parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                        }
                )

                if (service.main_contact.isTollFree) {
                    Text(
                        text = stringResource(id = R.string.message_toll_free).uppercase(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .clip(
                                MaterialTheme.shapes.extraSmall
                            )
                            .background(
                                MaterialTheme.colorScheme.primary
                            )
                            .padding(2.dp)
                            .constrainAs(badge) {
                                top.linkTo(textSubtitle.top)
                                bottom.linkTo(textSubtitle.bottom)
                                start.linkTo(textSubtitle.end, 10.dp)
                            }
                    )
                }

                if (actionVisible) {
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier
                            .constrainAs(iconDropDown) {
                                top.linkTo(parent.top, 8.dp)
                                end.linkTo(parent.end, 8.dp)
                            }
                    ) {
                        MesIcon(
                            imageVector = if (!expanded) Icons.Outlined.ArrowDropDown else Icons.Outlined.ArrowDropUp,
                            contentDescription = String.format(
                                stringResource(id = R.string.action_drop_down),
                                service.name
                            )
                        )
                    }
                }
            }

            if (expanded) MesServiceItemExtras(
                service = service,
                modifier = modifier,
                extrasClickAction = extrasClickAction
            )
        }

    }
}

@Composable
@Preview(name = "Mes MesSwipeAbleAction Light", showBackground = true)
@Preview(name = "Mes MesSwipeAbleAction Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun MesSwipeAbleServiceItemPreview() {
    val mockDataService = Service(
        identifier = "security-police-direct-1",
        name = "This is a very super duper long long and long",
        type = "E",
        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
        main_contact = 999,
        emails = listOf(),
        other_contacts = listOf()
    )

    MaterialTheme {
        MesSwipeAbleServiceItem(
            service = mockDataService,
            actionVisible = true,
            onClick = {},
            extrasClickAction = { _, _ -> }
        )
    }
}

//@Preview("Service Item Light", showBackground = true)
//@Preview("Service Item Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//@ExperimentalMaterial3Api
//fun MesServiceItemPreview() {
//    val mockDataService = Service(
//        identifier = "security-police-direct-1",
//        name = "This is a very super duper long long title",
//        type = "E",
//        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
//        main_contact = 999,
//        emails = listOf(),
//        other_contacts = listOf()
//    )
//
//    Column {
//        MesServiceItem(
//            service = mockDataService,
//            onClick = {},
//            extrasClickAction = { _, _ -> }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        MesServiceItemExtras(
//            service = mockDataService,
//            extrasClickAction = { _, _ -> },
//            modifier = Modifier
//        )
//    }
//}