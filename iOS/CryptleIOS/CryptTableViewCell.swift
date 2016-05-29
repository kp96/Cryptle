//
//  CryptTableViewCell.swift
//  CryptleIOS
//
//  Created by Sulabh Agarwal on 5/29/16.
//  Copyright © 2016 Sulabh Agarwal. All rights reserved.
//

import UIKit

class CryptTableViewCell: UITableViewCell {
    
    @IBOutlet var thumbnailImageView: UIImageView!
    

    @IBOutlet var nameLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        thumbnailImageView.layer.cornerRadius = thumbnailImageView.frame.width / 2
        thumbnailImageView.layer.masksToBounds = true

    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
