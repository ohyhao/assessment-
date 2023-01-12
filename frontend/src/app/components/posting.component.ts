import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Posting } from '../models';
import { ListingService } from '../services/listing.service';

@Component({
  selector: 'app-posting',
  templateUrl: './posting.component.html',
  styleUrls: ['./posting.component.css']
})
export class PostingComponent implements OnInit {

  posting!: Posting
  
  constructor(private listingSvc: ListingService, private router: Router) { }

  ngOnInit(): void {
    this.posting = history.state
    console.log(history.state)
  }

  confirmPost() {
    this.listingSvc.putPosting(this.posting.postingId, this.posting)
      .then(result => {
        alert(result.message)
        this.router.navigate(['/confirmation'], {state: this.posting})
      }).catch(error => {
        alert(error.message)
      })
  }

}
