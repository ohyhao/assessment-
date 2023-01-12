import { Component, OnInit } from '@angular/core';
import { Posting } from '../models';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent implements OnInit {

  posting!: Posting

  constructor() { }

  ngOnInit(): void {
    this.posting = history.state
    console.log(history.state)
  }

}
