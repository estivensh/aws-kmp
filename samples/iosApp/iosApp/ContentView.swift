import SwiftUI
import shared
import KMMViewModelSwiftUI
import PhotosUI

@available(iOS 16.0, *)
struct ContentView: View {
	
    @StateViewModel var sampleViewModel = SampleViewModel()
    let bucketName = "test-bucket-ios-app"
    let key = "test (4).jpg"
    @State private var selectedItem: PhotosPickerItem? = nil
    @State private var selectedImageData: Data? = nil
    
    var body: some View {
        PhotosPicker(
            selection: $selectedItem,
            matching: .images,
            photoLibrary: .shared()) {
                Text("Select a photo")
            }
            .onChange(of: selectedItem) { newItem in
                Task {
                    // Retrieve selected asset in the form of Data
                    if let data = try? await newItem?.loadTransferable(type: Data.self) {
                        selectedImageData = data
                    }
                }
            }
        
        if let selectedImageData,
           let uiImage = UIImage(data: selectedImageData) {
            Image(uiImage: uiImage)
                .resizable()
                .scaledToFit()
                .frame(width: 250, height: 250)
        }
        
        
        VStack {
            List {
                ForEach(sampleViewModel.bucketList, id: \.name){ data in
                    Text(data.name ?? "Default value")
                }
            }
            Text("Status bucket: \(sampleViewModel.bucket)")
            Text("Status generate presigned url: \(sampleViewModel.generatePresignedUrl)")
            Button(action: {
                sampleViewModel.createBucket(bucketName: bucketName)
            }, label: {
                Text("Create bucket")
            })
            Button(action: {
                sampleViewModel.listBuckets()
            }, label: {
                Text("List bucket")
            })
            Button(action: {
                sampleViewModel.deleteBucket(bucketName: bucketName)
            }, label: {
                Text("Delete bucket")
            })
            Button(action: {
                sampleViewModel.generatePresignedUrl(bucketName: bucketName, key: key)
            }, label: {
                Text("Generate presigned URL")
            })
        
            Button(action: {

                if let selectedImageData,
                   let uiImage = UIImage(data: selectedImageData) {
                    sampleViewModel.putObject(bucketName: bucketName, key: key, imageFile: .init(uiImage: uiImage))
                }
            
                
            }, label: {
                Text("Put object")
            })
        }
    }
}

