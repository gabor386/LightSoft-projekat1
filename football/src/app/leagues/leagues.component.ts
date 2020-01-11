import { Component, OnInit } from '@angular/core';
import { LeaguesService } from '../services/leagues.service';

@Component({
  selector: 'app-leagues',
  templateUrl: './leagues.component.html',
  styleUrls: ['./leagues.component.css']
})
export class LeaguesComponent implements OnInit {
  leagues: any [];

  constructor(private service: LeaguesService) { }

  ngOnInit() {
    this.service.getAll()
    .subscribe( leagues => this.leagues = leagues);

  }


}
