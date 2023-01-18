package com.th3pl4gu3.mes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.theme.MesTheme
import com.th3pl4gu3.mes.ui.utils.capitalize

@Composable
fun MesAnimatedVisibilityExpandedHorizontallyContent(
    visibility: Boolean,
    content: @Composable () -> Unit
){
    val duration = 500
    AnimatedVisibility(
        visible = visibility,
        enter = expandHorizontally(animationSpec = tween(durationMillis = duration)),
        exit = shrinkHorizontally(animationSpec = tween(durationMillis = duration))
    ){
        content()
    }
}

@Composable
fun MesIcon(
    imageVector: ImageVector,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    @StringRes contentDescription: Int? = null,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = imageVector,
        contentDescription = if (contentDescription != null) stringResource(id = contentDescription) else null,
        tint = tint,
        modifier = modifier
    )
}

@Composable
fun MesIcon(
    @DrawableRes painterResource: Int,
    @StringRes contentDescription: Int? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    Icon(
        painter = painterResource(id = painterResource),
        contentDescription = if (contentDescription != null) stringResource(id = contentDescription) else null,
        tint = tint
    )
}

@Composable
fun MesAsyncRoundedImage(service: Service, size: Dp = 64.dp, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = modifier
            .size(size)
            .padding(8.dp)
            .clip(RoundedCornerShape(50)),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(service.icon)
            .crossfade(true)
            .build(),
        contentDescription = service.name,
        error = painterResource(R.drawable.ic_image_broken),
        placeholder = painterResource(R.drawable.ic_image_loading),
    )
}

@Composable
fun MesLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {

        Spacer(Modifier.width(8.dp))

        MesTitleLogo()
    }
}

@Composable
fun MesTitleLogo(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.app_name).lowercase(),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun MesServiceItem(
    service: Service,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {

            val (
                textTitle,
                textSubtitle,
                iconEmergency,
                iconDropDown,
                iconCall,
            ) = createRefs()

            MesAsyncRoundedImage(
                service = service,
                modifier = Modifier
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
                style = MaterialTheme.typography.headlineLarge,
                softWrap = true,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .constrainAs(textTitle) {
                        top.linkTo(iconEmergency.top)
                        bottom.linkTo(textSubtitle.top, 4.dp)
                        linkTo(
                            start = iconEmergency.end,
                            end = iconCall.start,
                            startMargin = 8.dp,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = service.number.toString(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .constrainAs(textSubtitle) {
                        top.linkTo(textTitle.bottom, 4.dp)
                        bottom.linkTo(iconEmergency.bottom)
                        linkTo(
                            start = iconEmergency.end,
                            end = iconCall.start,
                            startMargin = 12.dp,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                    }
            )

            MesIcon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(iconDropDown){
                        top.linkTo(parent.top, 8.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
            )

            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .constrainAs(iconCall) {
                        bottom.linkTo(parent.bottom, 8.dp)
                        end.linkTo(parent.end, 24.dp)
                    }
            ) {
                MesIcon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = R.string.ctnt_desc_phone_button
                )
            }

        }

//
//        Column {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//
//                MesAsyncRoundedImage(service = service, modifier = modifier)
//
//                Column {
//                    Text(
//                        text = service.name.capitalize(),
//                        style = MaterialTheme.typography.headlineLarge,
//                        color = MaterialTheme.colorScheme.onSurface,
//                        modifier = modifier.padding(top = 8.dp)
//                    )
//                    Text(
//                        text = service.number.toString(),
//                        style = MaterialTheme.typography.headlineSmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        modifier = modifier.padding(4.dp)
//                    )
//                }
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                IconButton(
//                    onClick = onClick
//                ) {
//                    MesIcon(
//                        imageVector = Icons.Outlined.Phone,
//                        contentDescription = R.string.ctnt_desc_phone_button
//                    )
//                }
//            }
//        }
//    }
    }
}

@Composable
@ExperimentalMaterial3Api
fun MesEmergencyItem(
    service: Service,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        modifier = modifier.padding(8.dp)
    ) {

        ConstraintLayout(
            modifier = modifier.width(240.dp)
        ) {

            val (
                textTitle,
                emergencyIcon
            ) = createRefs()


            Text(
                text = service.name.capitalize(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .constrainAs(textTitle) {
                        with(16.dp) {
                            top.linkTo(parent.top, this)
                            start.linkTo(parent.start, this)
                            end.linkTo(parent.end, this)
                            bottom.linkTo(emergencyIcon.top, this)
                        }
                    },
            )

            MesAsyncRoundedImage(
                service = service,
                modifier = modifier
                    .padding(8.dp)
                    .constrainAs(emergencyIcon) {
                        with(16.dp) {
                            bottom.linkTo(parent.bottom, this)
                            start.linkTo(parent.start, this)
                            end.linkTo(parent.end, this)
                        }
                    },
            )
        }
    }

}

@Composable
fun MesEmergencyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = MaterialTheme.colorScheme.secondary),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.outlineVariant),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
    ) {
        // Adding an Icon "Add" inside the Button
        MesIcon(
            painterResource = R.drawable.ic_emergency_beacons,
            contentDescription = R.string.ctnt_desc_emergency_button,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}


@Preview(name = "Mes Composable Light", showBackground = true)
@Preview(name = "Mes Composable Dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalMaterial3Api
fun MesComposablePreview() {

    val mockData = Service(
        identifier = "security-police-direct-2",
        name = "Samu",
        type = "E",
        icon = "https://img.icons8.com/fluent/100/000000/policeman-male.png",
        number = 112
    )

    MesTheme {
        Column {
            MesEmergencyItem(
                service = mockData,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(24.dp))

            MesIcon(
                painterResource = R.drawable.ic_image_broken,
            )

            Spacer(modifier = Modifier.height(24.dp))

            MesIcon(
                imageVector = Icons.Outlined.Phone,
            )

            Spacer(modifier = Modifier.height(24.dp))

            MesServiceItem(service = mockData) {

            }
        }
    }
}
