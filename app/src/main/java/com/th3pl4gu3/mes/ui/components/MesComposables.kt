package com.th3pl4gu3.mes.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.th3pl4gu3.mes.R

@Composable
fun MesIcon(
    imageVector: ImageVector,
    @StringRes contentDescription: Int? = null
){
    Icon(
        imageVector = imageVector,
        contentDescription = if(contentDescription != null) stringResource(id = contentDescription) else null,
        tint = MaterialTheme.colorScheme.onSurface
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