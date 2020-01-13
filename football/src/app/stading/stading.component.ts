import { Component, OnInit } from '@angular/core';
import { StadingService } from '../services/stading.service';
import { LeaguesService } from '../services/leagues.service';
import { ActivatedRoute } from '@angular/router';
import 'rxjs/add/operator/filter';


@Component({
  selector: 'app-stading',
  templateUrl: './stading.component.html',
  styleUrls: ['./stading.component.css']
})

export class StadingComponent {
  stading$;
  league: any;

  constructor(  router: ActivatedRoute, serviceStading: StadingService ) {
    router.queryParamMap.subscribe( params => {
      this.league = params.get('league');
      if (this.league != null){
      this.stading$ = serviceStading.getById(this.league);
      }

      });

  }


  // ngOnInit() {

  //   this.router.queryParams
  //   .filter(params => params.league)
  //   .subscribe(params => {
  //     this.league = params.league;
  //     this.serviceStading.getById(this.league).subscribe(stading => this.stading = stading);

  //   });


  // }

}
