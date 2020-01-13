
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
import { TeamComponent } from './team/team.component';
import { HttpClientModule } from '@angular/common/http';
import { TeamService } from './services/team.service';
import { FixturesComponent } from './fixtures/fixtures.component';
import { FixtureService } from './services/fixtures.service';
import { FixturesnsComponent } from './fixturesns/fixturesns.component';
import { FixtureServiceNs } from './services/fixturesns.service';


@NgModule({
  declarations: [
    AppComponent,
    StadingComponent,
    BsNavbarComponent,
    LeaguesComponent,
    HomeComponent,
    StadingComponent,
    TeamComponent,
    
    FixturesComponent,
    FixturesnsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpModule,
    RouterModule.forRoot([
      {path: 'teams' , component: TeamComponent},
      {path: 'fixtures' , component: FixturesComponent}
      {path: 'stading' , component: StadingComponent },
      {path: 'leagues' , component: LeaguesComponent }
      {path: '' , component: HomeComponent },
    ])
  ],
  providers: [LeaguesService,FixtureService,FixtureServiceNs,TeamService],
  providers: [LeaguesService, StadingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
