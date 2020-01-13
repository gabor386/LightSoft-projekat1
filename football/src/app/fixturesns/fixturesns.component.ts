import { Component, OnInit } from '@angular/core';
import { FixtureServiceNs } from '../services/fixturesns.service';

import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/operator/filter';

@Component({
  selector: 'app-fixturesns',
  templateUrl: './fixturesns.component.html',
  styleUrls: ['./fixturesns.component.css']
})
export class FixturesnsComponent implements OnInit {

  fixtures: any [];
  league: any;
  constructor(private service: FixtureServiceNs, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams
    .filter(params => params.league)
    .subscribe(params => {
      this.league = params.league;
      this.service.getById(this.league)
      .subscribe( fixtures => { this.fixtures = fixtures;
      });

    });
    
  }

}

