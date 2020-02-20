using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace HC
{
    public class SampleSolution : Solution
    {
        public override string[] Files { get => new[] { "a_example.in" }; }
        public override string Resources { get =>
                Path.Combine(Directory.GetCurrentDirectory(), @"..\..\..\..\..\..\resources"); 
                }

        private int A;
        private int B;

        public SampleSolution(int repeat, int fileIndex): base(repeat, fileIndex)
        {
        }

        public override long Compute()
        {
            Random r = new Random();
            return r.Next(0, 100);
        }

        public override void Input(InputFile inputFile)
        {
            A = inputFile.GetInt(0, 0);
            B = inputFile.GetInt(0, 1);
            var array = inputFile.GetMatrix();
            WriteLog(array, '|');
        }

        public override string[] Output()
        {
            return new[] { B.ToString(), A.ToString() };
        }
    }
}
