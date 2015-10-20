select weapon, count(id) as kills from 

where weapon != 'world'
group by weapon order by kills desc;



select weapon.class,  b.weapon, sum(numberWeapon) as sum, 100*sum(numberWeapon)/total, b.week, b.year, count(id_logfile) from 
(

select weapon, count(activeplayer) as numberWeapon, EXTRACT(WEEK from date) as week, EXTRACT(YEAR from date) as year, id_logfile from 

(select distinct activeplayer, weapon,  date, id_logfile from killevent) as a

where weapon != 'world' and weapon != '200' and weapon !='a'
group by weapon, date, id_logfile order by weapon, year, date 
) as b, weapon,



(select sum(numberWeapon) as total, class, slot, week, year from 

(

select weapon, count(activeplayer) as numberWeapon, class, slot, EXTRACT(WEEK from date) as week, EXTRACT(YEAR from date) as year, id_logfile from 

(select distinct activeplayer, weapon,  date, id_logfile from killevent) as a, weapon

where weapon != 'world' and weapon != '200' and weapon !='a' and weapon.name=weapon 
group by weapon, class, slot, date, id_logfile order by year, week
) as j group by year, week, class, slot order by year, week

)
as c


where b.weapon = weapon.name and weapon.class = 'demoman' 
and weapon.slot = 1 and c.week = b.week and c.year = b.year and weapon.slot = c.slot and weapon.class = c.class

 group by weapon.class, weapon, b.week, b.year, total order by weapon, b.year, b.week