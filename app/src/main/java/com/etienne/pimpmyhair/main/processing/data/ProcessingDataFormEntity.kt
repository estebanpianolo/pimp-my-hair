package com.etienne.pimpmyhair.main.processing.data

import com.google.gson.annotations.SerializedName

data class ProcessingDataFormEntity(@SerializedName("b64_img") val base64Image: String)
