using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using System.IO;

namespace HC
{
    public abstract class Solution : Writer
    {
        public abstract string[] Files { get;}

        public abstract string Resources { get;}

        public abstract void Input(InputFile inputFile);

        public abstract string[] Output();

        public abstract long Compute();

        public Solution(int repeat, int fileIndex)
        {
            var best = Read(fileIndex);
            foreach(var x in Enumerable.Range(0, repeat))
            {
                var score = Compute();
                if (score > best)
                {
                    Write(fileIndex, score);
                    best = score;
                }
            }

            Console.WriteLine("best: " + best);
        }

        public string Filename(int fileIndex, long score) 
            => Path.Combine(Resources,Path.GetFileNameWithoutExtension(this.Files[fileIndex]) + score + ".out");

        public void Write(int fileIndex, long score) {
            Console.WriteLine($"File {fileIndex} new top score: {score}");
            WriteFile(Filename(fileIndex, score), Output());
        }

        public long Read(int fileIndex){
            var filename = Path.Combine(Resources, this.Files[fileIndex]);
            Input(new InputFile(filename));
            // check existing top score
            var name = Path.GetFileNameWithoutExtension(this.Files[fileIndex]);
            var filenames = InputFile.FilesInDir(Resources);
            var scores = filenames.Where(f => f.StartsWith(name) && f.EndsWith(".out")).Select(f => long.Parse(Path.GetFileNameWithoutExtension(f).Remove(0, name.Length + 1)));
            return scores.Count() == 0 ? 0L : scores.Max();
        }
  }
}
