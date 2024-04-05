package com.jeekim.server.jocr.client.bucket

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.CopyObjectRequest
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.jeekim.server.jocr.domain.entity.Prescription
import com.jeekim.server.jocr.domain.enums.ServiceType
import org.springframework.web.multipart.MultipartFile
import java.net.URL
import java.time.LocalDate
import java.util.UUID

class BucketAdapter(
    private val amazonS3Client: AmazonS3Client,
    private val bucketName: String
) {

    private fun getResourceUrl(fileKey: String): String {
        return amazonS3Client.getUrl(bucketName, fileKey).toString()
    }

    fun uploadTemp(multipartFile: MultipartFile, uploadPath: String): String {
        val metadata = ObjectMetadata()
        metadata.contentType = "image/jpeg"
        val inputStream = multipartFile.inputStream
        val request = PutObjectRequest(bucketName, uploadPath, inputStream, metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead) // Set the file to be publicly accessible

        amazonS3Client.putObject(request)
        return uploadPath
    }
    /**
     * move file from temp to real
     */

    fun changeNameOfFile(url: String, prescription: Prescription): String {
        val originalKey = getKeyFromUrl(url)
        val extension = originalKey.substringAfterLast('.', "") // Extract the extension

        val newFileName = createFileName(prescription, extension) // Create the new filename
        val directoryPath = originalKey.substringBeforeLast('/') + "/"

        val newKey = "$directoryPath$newFileName" // New key in the same directory but with the new filename
        // Copy the original object to the new key (rename)
        val copyObjectRequest = CopyObjectRequest(bucketName, originalKey, bucketName, newKey)
        amazonS3Client.copyObject(copyObjectRequest)

        // Delete the original object
        val deleteObjectRequest = DeleteObjectRequest(bucketName, originalKey)
        amazonS3Client.deleteObject(deleteObjectRequest)

        // Return the URL of the object with the new name
        return getResourceUrl(newKey)

    }

    private fun createFileName(prescription: Prescription, extension: String): String {
        val issuanceNumber = prescription.issuanceNumber
        val patientName = prescription.patientName
        val hospital = prescription.hospitalName
        val patientRrnFirst = prescription.patientRrnFirst
        return "$issuanceNumber-$hospital-$patientName-$patientRrnFirst.$extension"
    }

    private fun getKeyFromUrl(url: String): String {
        val urlObj = URL(url)
        return urlObj.path.substringAfter("/$bucketName/")
    }



}