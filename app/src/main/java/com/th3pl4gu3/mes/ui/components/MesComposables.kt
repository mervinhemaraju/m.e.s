package com.th3pl4gu3.mes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.th3pl4gu3.mes.R
import com.th3pl4gu3.mes.models.Service
import com.th3pl4gu3.mes.ui.theme.MesTheme
import com.th3pl4gu3.mes.ui.utils.capitalize

@Composable
fun MesIcon(
    imageVector: ImageVector,
    @StringRes contentDescription: Int? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface
){
    Icon(
        imageVector = imageVector,
        contentDescription = if(contentDescription != null) stringResource(id = contentDescription) else null,
        tint = tint
    )
}

@Composable
fun MesIcon(
    @DrawableRes painterResource: Int,
    @StringRes contentDescription: Int? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface
){
    Icon(
        painter = painterResource(id = painterResource),
        contentDescription = if(contentDescription != null) stringResource(id = contentDescription) else null,
        tint = tint
    )
}

@Composable
fun MesLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
//        MesIcon(
//            imageVector = Icons.Outlined.Phone,
//            contentDescription = null,
//        )
        Spacer(Modifier.width(8.dp))

        MesTitleLogo()
    }
}

@Composable
fun MesTitleLogo(modifier: Modifier = Modifier){
    Text(
        text = stringResource(id = R.string.app_name).lowercase(),
        style=MaterialTheme.typography.titleLarge,
        color=MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun MesServiceItem(
    service: Service,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
){

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

//                Image(
//                    modifier = modifier
//                        .size(64.dp)
//                        .padding(8.dp)
//                        .clip(RoundedCornerShape(50)),
//                    contentScale = ContentScale.Crop,
//                    imageVector = Icons.Filled.Home,
//                    /*
//                     * Content Description is not needed here - image is decorative, and setting a null content
//                     * description allows accessibility services to skip this element during navigation.
//                     */
//                    contentDescription = null
//                )
                AsyncImage(
                    modifier = modifier
                        .size(64.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(50)),
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(service.icon)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Service Icon",
                    error = painterResource(R.drawable.ic_image_broken),
                    placeholder = painterResource(R.drawable.ic_image_loading),
                )

                Column {
                    Text(
                        text = service.name.capitalize(),
                        style = MaterialTheme.typography.headlineLarge,
                        color=MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = service.number.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        color=MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = modifier.padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onClick
                ) {
                    MesIcon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = R.string.ctnt_desc_phone_button
                    )
                }
            }
        }
    }
}

@Composable
fun MesEmergencyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    ElevatedButton(
        onClick = onClick,
        modifier= modifier,
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

///**
// * Composable Previews
// **/
//
//@Preview("MesServiceItem Light")
//@Preview("MesServiceItem Light", uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun MesServiceItemPreview(){
//
//    // Create a dummy service for preview
//    val service = Service(
//        identifier = "id-01",
//        name = "Police",
//        type = "E",
//        number = 999,
//        icon = "url"
//    )
//
//    MesTheme {
//        MesServiceItem(
//            service = service,
//            onClick = {}
//        )
//    }
//}