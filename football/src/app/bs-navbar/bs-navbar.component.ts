import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../services/leagues.service';
import { StadingComponent } from '../stading/stading.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bs-navbar',
  templateUrl: './bs-navbar.component.html',
  styleUrls: ['./bs-navbar.component.css']
})
export class BsNavbarComponent {
  leagues: any [];
  league: any ;
 

  constructor( router: ActivatedRoute, service: LeaguesService) {
    router.queryParamMap.subscribe( params => {
      this.league = params.get('league');
      });

   }



}
