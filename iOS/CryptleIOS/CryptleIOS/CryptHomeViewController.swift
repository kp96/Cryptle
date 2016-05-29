//
//  CryptHomeViewController.swift
//  CryptleIOS
//
//  Created by Sulabh Agarwal on 5/29/16.
//  Copyright © 2016 Sulabh Agarwal. All rights reserved.
//

import UIKit
import Alamofire
import ImageLoader
import SwiftyJSON

class CryptHomeViewController: UITableViewController,UISearchResultsUpdating {

    var happyHours = [HappyHour]()
    var searchController:UISearchController!
    var searchResults:[HappyHour] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Home"
        loadPosts()
        searchController = UISearchController(searchResultsController: nil)
        tableView.tableHeaderView = searchController.searchBar
        searchController.searchResultsUpdater = self
        searchController.dimsBackgroundDuringPresentation = false
        
        searchController.searchBar.placeholder = "Search family..."
        searchController.searchBar.tintColor = UIColor.whiteColor()
        searchController.searchBar.barTintColor = UIColor(red: 237.0/255.0, green:
            139.0/255.0, blue: 28.0/255.0, alpha: 1.0)
       
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func loadPosts()
    {
        let testhappyhour:HappyHour = HappyHour(images: "TEST", name: "TEST")
        //    self.happyHours.append(testhappyhour)
        
        let url:String = "http://enigmatic-woodland-35608.herokuapp.com/restaurants.json"
        Alamofire.request(.GET, url, encoding:.JSON).responseJSON
            { response in switch response.result {
            case .Success(let JSON):
                let response = JSON as! NSArray
                print(response)
                for item in response { // loop through data items
                    let obj = item as! NSDictionary
                    let happyhour = HappyHour(images:obj["thumbnail"] as! String, name:obj["hotelname"] as! String)
                    self.happyHours.append(happyhour)
                }
                self.tableView.reloadData()
                
                
            case .Failure(let error):
                print("Request failed with error: \(error)")
                }
        }
    }
    
    func filterContextForSearchText(searchText: String) {
        searchResults = happyHours.filter({ (restaurant:HappyHour) -> Bool in
            let nameMatch = restaurant.name.rangeOfString(searchText, options: NSStringCompareOptions.CaseInsensitiveSearch)
            return nameMatch != nil
            
        })
    }
    
    func updateSearchResultsForSearchController(searchController: UISearchController) {
        if let searchText = searchController.searchBar.text {
            filterContextForSearchText(searchText)
            tableView.reloadData()
        }
    }
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        if searchController.active
        {
            return false
        }
        else
        {
            return true
        }
    }
    

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        if searchController.active{
            return searchResults.count
        }
        else
        {
            return happyHours.count
        }

    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath) as! CryptTableViewCell
        
        let happyHour = (searchController.active) ? searchResults[indexPath.row] : happyHours[indexPath.row]
        
        cell.nameLabel?.text = happyHour.name
        if let url = NSURL(string: happyHour.images)
        {
            cell.thumbnailImageView.load(url)
        }
        else
        {
            
        }
        return cell
        
    }
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        if segue.identifier == "showDetail"
        {
            if let indexPath = tableView.indexPathForSelectedRow
            {
                let destinationController = segue.destinationViewController as!
                DetailViewController
                
                destinationController.happyHour = (searchController.active) ? searchResults[indexPath.row] : happyHours[indexPath.row]
            }
        }
            
        else
        {
//            let menuTableViewController = segue.destinationViewController as!
//            MenuTableViewController
//            menuTableViewController.currentItem = self.title!
//            menuTableViewController.transitioningDelegate = menuTransitionManager
//            menuTransitionManager.delegate = self
        }
        
        //   menuTableViewController.currentItem = self.title!
        //    menuTableViewController.transitioningDelegate = menuTransitionManager
        //   menuTransitionManager.delegate = self
    }


    /*
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("reuseIdentifier", forIndexPath: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
