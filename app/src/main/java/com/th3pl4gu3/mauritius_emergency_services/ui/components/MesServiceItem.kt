package com.th3pl4gu3.mauritius_emergency_services.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
@ExperimentalMaterial3Api
fun MesServiceItem(
    service: Service,
    actionVisible: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val baseModifier = modifier
        .fillMaxWidth()
        .background(
            if (expanded) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.background
            }
        )

    if (actionVisible) {

        val startCall = SwipeAction(
            onSwipe = onClick,
            icon = {
                MesIcon(
                    imageVector = Icons.Outlined.PhoneInTalk,
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .graphicsLayer {
                            rotationZ = -100f
                        },
                )
            },
            background = MaterialTheme.colorScheme.primary,
        )

        SwipeableActionsBox(
            swipeThreshold = 120.dp,
            endActions = listOf(startCall),
            backgroundUntilSwipeThreshold = MaterialTheme.colorScheme.inverseSurface,
            modifier = baseModifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            MesServiceItemLayout(
                service = service,
                dropDownClick = { expanded = !expanded },
                onClick = onClick,
                expanded = expanded,
                actionVisible = true,
                modifier = baseModifier
            )
        }

    } else {
        MesServiceItemLayout(
            service = service,
            dropDownClick = {},
            onClick = onClick,
            expanded = false,
            actionVisible = false,
            modifier = Modifier
        )
    }
}

@Composable
@ExperimentalMaterial3Api
fun MesServiceItemLayout(
    service: Service,
    dropDownClick: () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean,
    actionVisible: Boolean,
    modifier: Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {

        ConstraintLayout(
            modifier = modifier
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
                style = MaterialTheme.typography.bodyLarge,
                softWrap = true,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .constrainAs(textTitle) {
                        top.linkTo(iconEmergency.top)
                        bottom.linkTo(textSubtitle.top, 4.dp)
                        linkTo(
                            start = iconEmergency.end,
                            end = parent.end,
                            startMargin = 8.dp,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = service.main_contact.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .constrainAs(textSubtitle) {
                        top.linkTo(textTitle.bottom)
                        bottom.linkTo(iconEmergency.bottom)
                        linkTo(
                            start = iconEmergency.end,
                            end = parent.end,
                            startMargin = 12.dp,
                            endMargin = 16.dp,
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
                    onClick = dropDownClick,
                    modifier = Modifier
                        .constrainAs(iconDropDown) {
                            top.linkTo(parent.top, 8.dp)
                            end.linkTo(parent.end, 8.dp)
                        }
                ) {
                    MesIcon(
                        imageVector = if (!expanded) Icons.Outlined.ArrowDropDown else Icons.Outlined.ArrowDropUp
                    )
                }
            }
        }

        if (expanded) MesServiceItemExtras(service = service, modifier = modifier)
    }
}

@Composable
@ExperimentalMaterial3Api
fun MesServiceItemExtras(
    service: Service,
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
                    onClick = {},
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
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = modifier
                .padding(16.dp)
        )
    }
}

@Preview("Service Item Light", showBackground = true)
@Preview("Service Item Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun MesServiceItemPreview() {
    val mockDataService = Service(
        identifier = "security-police-direct-1",
        name = "Police Direct Line 1",
        type = "E",
        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
        main_contact = 999,
        emails = listOf(),
        other_contacts = listOf()
    )

    Column {
        MesServiceItem(
            service = mockDataService,
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        MesServiceItemExtras(
            service = mockDataService,
            modifier = Modifier
        )
    }
}