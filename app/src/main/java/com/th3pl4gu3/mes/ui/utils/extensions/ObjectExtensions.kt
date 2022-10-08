package com.th3pl4gu3.mes.ui.utils.extensions

import com.th3pl4gu3.mes.api.Service
import com.th3pl4gu3.mes.ui.utils.helpers.Global

// Simple concatenate string resource with string variable
fun String.join(piece: String) = String.format(this, piece)

// Filter services and return the emergency button service
inline val List<Service>.requireEmergencyButton: Service
    // Assign the police direct line 1 service as emergency button
    get() = this.first {
        it.identifier == Global.ID_API_SERVICE_EMERGENCY_BUTTON
    }

// Remove Emergency Button Service from the list
inline val List<Service>.withoutEmergencyButtonService: List<Service>
    get() = ArrayList(this).apply {
        // Remove the emergency button service from other emergencies list
        this.removeIf { it.identifier == Global.ID_API_SERVICE_EMERGENCY_BUTTON }
    }

