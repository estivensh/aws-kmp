//
//  AppDelegate.swift
//  iosApp
//
//  Created by Estiven on 13/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import UIKit
import AWSS3
import shared


class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        //initializeS3()
        return true
        
    }
}

func initializeS3(){

    let credentialsProvider = AWSStaticCredentialsProvider(accessKey: BuildPublicConfig().accessKey, secretKey: BuildPublicConfig().secretKey)
    let configuration = AWSServiceConfiguration.init(region: .USEast1, credentialsProvider: credentialsProvider)

    AWSServiceManager.default().defaultServiceConfiguration = configuration
}
