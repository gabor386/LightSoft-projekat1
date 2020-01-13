
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
import { TeamComponent } from './team/team.component';
import { HttpClientModule } from '@angular/common/http';
import { TeamService } from './services/team.service';


@NgModule({
  declarations: [
    AppComponent,
    BsNavbarComponent,
    LeaguesComponent,
    HomeComponent,
    StadingComponent,
    TeamComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpModule,
    RouterModule.forRoot([
      {path: '' , component: HomeComponent},
      {path: '' , component: TeamComponent},
      {path: 'leagues' , component: LeaguesComponent}
    ])
  ],
  providers: [LeaguesService,TeamService],
  bootstrap: [AppComponent]
})
export class AppModule { }
