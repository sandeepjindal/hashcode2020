using System;
using System.IO;

namespace HC
{
    class Program
    {
        private static readonly InputFile _inputFile = new InputFile("a_example.in");

        static void Main(string[] args)
        {
            Console.WriteLine("Hello Green Agojis (:");
            var solution = new SampleSolution(2, 0);
            var matrix = new[] { new int[] { 1, 14, 3, 4, 5 }, new int[] { 6, 7, 8, 9, 10 } };

            Writer.WriteLog(matrix, '|', 2);
        }
    }
}
