package com.example.adminpkart

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminpkart.databinding.FragmentAddCategoryBinding
import com.example.adminpkart.databinding.FragmentCategoryBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class CategoryFragment : Fragment() {
lateinit var binding: FragmentCategoryBinding
    private var ImageUrl : Uri? = null
    lateinit var db:FirebaseStorage

    private var launchGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult ()){

        if(it.resultCode== Activity.RESULT_OK)
            ImageUrl = it.data!!.data
        binding.categoryImg.setImageURI(ImageUrl)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCategoryBinding.inflate(layoutInflater)
        getData()



        binding.apply {

            categoryImg.setOnClickListener {

                val intent = Intent("android.intent.action.GET_CONTENT")
                intent.type = "image/*"
                launchGallery.launch(intent)

            }

            binding.categoryBtn.setOnClickListener {
                if (ImageUrl != null){
                    uploadImage(ImageUrl!!)

                }

            }



        }

        return binding.root
    }

    private fun getData() {
       val list_bom  = ArrayList<model_bom>()
        Firebase.firestore.collection("category")
            .get().addOnSuccessListener {
                for (doc in it.documents){

                    val data = doc.toObject(model_bom::class.java)
                    list_bom.add(data!!)
                }
                binding.rcvCate.layoutManager  = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
                binding.rcvCate.adapter =adapter_bom(requireContext(),list_bom)
            }
    }

    private fun uploadImage(uri: Uri) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val refstorage =
            FirebaseStorage.getInstance().reference.child("category/$fileName")
        refstorage.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                // Get the download URL from the taskSnapshot
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    storeData(downloadUri.toString()) // Pass the download URL to storeData function
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "SOME THING WENT WRONG", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "SOME THING WENT WRONG", Toast.LENGTH_SHORT).show()
            }

    }
    private fun storeData(image: String) {
        val db = Firebase.firestore

        // Generate a new document ID for each image
        val newDocumentRef = db.collection("category").document()

        val data = hashMapOf<Any, String>("img" to image)
        newDocumentRef.set(data).addOnSuccessListener {
            binding.categoryImg.setImageURI(null)
            getData() // Refresh the RecyclerView to show all images

            Toast.makeText(requireContext(), "UPLOADED", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(requireContext(), "FAILED UPLOAD", Toast.LENGTH_SHORT).show()

        }
    }


}