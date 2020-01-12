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
  idLeage: any;
  constructor(private service: FixtureServiceNs, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams
    .filter(params => params.leagues)
    .subscribe(params => {
      this.idLeage = params.leagues;
      this.service.getById(this.idLeage)
      .subscribe( fixtures => { this.fixtures = fixtures;
      });

    });
    
  }

}

