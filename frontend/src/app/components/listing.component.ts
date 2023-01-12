import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Posting } from '../models';
import { ListingService } from '../services/listing.service';

@Component({
  selector: 'app-listing',
  templateUrl: './listing.component.html',
  styleUrls: ['./listing.component.css']
})
export class ListingComponent implements OnInit {

  @ViewChild('file') imageFile!: ElementRef;
  
  form!: FormGroup
  posting!: Posting
  
  constructor(private fb: FormBuilder, private listingSvc: ListingService, private router: Router) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      email: this.fb.control<string>('', [ Validators.required, Validators.email, Validators.maxLength(128)]),
      phone: this.fb.control<string>(''),
      title: this.fb.control<string>('', [Validators.required, Validators.minLength(5), Validators.maxLength(128)]),
      description: this.fb.control<string>('', [Validators.required]),
      file: this.fb.control('', [Validators.required])
    })
  }

  processForm() {
    const formData = new FormData;
    formData.set('name', this.form.value.name)
    formData.set('email', this.form.value.email)
    formData.set('phone', this.form.value.phone)
    formData.set('title', this.form.value.title)
    formData.set('description', this.form.value.description)
    formData.set('myfile', this.imageFile.nativeElement.files[0])
    console.log('>>> formdata: ', formData)
    this.listingSvc.postListing(formData)
      .then(result => {
        this.posting = result
        console.log('postingFromBackEnd: ', this.posting)
        this.router.navigate(['/post'], {state: this.posting}) 
      }).catch(error => {
        console.log(error)
      })
  }

}
