import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ListingComponent } from './components/listing.component';
import { PostingComponent } from './components/posting.component';
import { ConfirmationComponent } from './components/confirmation.component';
import { ListingService } from './services/listing.service';

const appRoutes: Routes = [
  {path: '', component: ListingComponent},
  {path: 'post', component: PostingComponent},
  {path: 'confirmation', component: ConfirmationComponent},
  {path: '**', redirectTo: '/', pathMatch: 'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    ListingComponent,
    PostingComponent,
    ConfirmationComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [ListingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
