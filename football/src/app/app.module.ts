
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {RouterModule} from '@angular/router';


import { AppComponent } from './app.component';
import { BsNavbarComponent } from './bs-navbar/bs-navbar.component';
import { LeaguesComponent } from './leagues/leagues.component';
import { HomeComponent } from './home/home.component';
import { LeaguesService } from './services/leagues.service';
import { HttpModule } from '@angular/http';
import { StadingComponent } from './stading/stading.component';


@NgModule({
  declarations: [
    AppComponent,
    BsNavbarComponent,
    LeaguesComponent,
    HomeComponent,
    StadingComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule.forRoot([
      {path: '' , component: HomeComponent},
      {path: 'leagues' , component: LeaguesComponent}
    ])
  ],
  providers: [LeaguesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
