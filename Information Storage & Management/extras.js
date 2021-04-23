// -----------------------------------------------------------
// Broken or other things


db.students.find({name:"Smith"}, {grade_reports:1}).pretty()

db.courses.find({course_name:"Database", sections:{$elemMatch:{semester:"Fall", year:"08"}}}, {_id:0, sections:1}).pretty()
db.courses.find({course_name:"Database", sections:{$elemMatch:{semester:"Fall", year:"08"}}}, {_id:0, sections:{section_identifier:1}}).pretty()

db.students.aggregate([
    {
        $lookup:{
            from: 'courses',
            let: {grade_reports: "$grade_reports"},
            pipeline: [
                {
                    $match: {
                        course_name:"Database",
                        sections:{$elemMatch:{semester:"Fall", year:"08"}}
                    },
                },
                {
                    $match: {
                        $expr:{
                            $in: ["$sections.section_identifier", "$$grade_reports.section_identifier"]
                        }
                    }
                }
            ],
            as: 'section_identifier'
            },
    }
]).pretty()

,
    {$match:{
        course_name:"Database",
        sections:{$elemMatch:{semester:"Fall", year:"08"}}
    }}


{
    $lookup:{
        from:"students",
        localField:"sections.section_identifier",
        foreignField:"grade_reports.section_identifier",
        as:"section"
    }

db.courses.aggregate([{"$replaceRoot": { 
    "newRoot": {
        "$arrayElemAt": [
            { "$filter": {
               "input": "$sections",
               "as": "section",
               "$elemMatch":{semester:"Fall", year:"08"} 
            } },
            0
        ]
    }
} }])

db.students.find({section_identifier:(db.courses.find({course_name:"Database", sections:{$elemMatch:{semester:"Fall", year:"08"}}}, {_id:0, sections:1}))})


db.courses.find({course_number:{$in:[db.courses.find({course_name:"Database"}, {_id:0, prerequisites:1})]}})


// db.courses.find({course_number:{$in:db.students.distinct("grade_reports", {name:"Smith"})}})

// db.students.distinct("grade_reports", {name:"Smith"})

// db.courses.find({course_number: {$elemMatch:db.students.find({name:"Smith"}, {grade_reports:1})}})

// pipeline: [
//     { $match: { sections.section_identifier: sections.section_identifier } },
//     { $replaceRoot: { newRoot: "$course_number" } }
//  ],


db.courses.find({name:"Database", sections:{$in:[semester:"Fall",year:"08"]}})
