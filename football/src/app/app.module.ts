
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {RouterModule , Routes} from '@angular/router';


import { AppComponent } from './app.component';
import { BsNavbarComponent } from './bs-navbar/bs-navbar.component';
import { LeaguesComponent } from './leagues/leagues.component';
import { HomeComponent } from './home/home.component';
import { LeaguesService } from './services/leagues.service';
import { HttpModule } from '@angular/http';
import { StadingComponent } from './stading/stading.component';
import { StadingService } from './services/stading.service';


@NgModule({
  declarations: [
    AppComponent,
    StadingComponent,
    BsNavbarComponent,
    LeaguesComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule.forRoot([
      {path: '' , component: HomeComponent },
     // {path: '' , component: StadingComponent },
      {path: 'stading' , component: StadingComponent },
      {path: 'leagues' , component: LeaguesComponent }
    ])
  ],
  providers: [LeaguesService, StadingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
