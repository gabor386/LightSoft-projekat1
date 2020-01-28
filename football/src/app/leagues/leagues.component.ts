import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../services/leagues.service';
import { StadingService } from '../services/stading.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-leagues',
  templateUrl: './leagues.component.html',
  styleUrls: ['./leagues.component.css']
})
export class LeaguesComponent {
  leagues$ = null;
  league: any;
  choise: any;

  constructor( router: ActivatedRoute , leagueService: LeaguesService) {
    router.queryParamMap.subscribe( params => {
      this.league = params.get('league');
      this.choise = params.get('choise');
      if(this.choise=='home'){
        this.choise='fixtures'
      }
    });
    this.leagues$ = leagueService.getAll();
  }


}
